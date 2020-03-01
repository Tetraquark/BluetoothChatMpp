package ru.tetraquark.mpp.bluetooth

import android.bluetooth.*
import android.os.Build
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.tetraquark.mpp.bluetooth.extensions.offerSafety
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext

typealias ConnectionState = Pair<Int, BLEConnectionState>

actual class BLEGattConnection internal constructor(
    actual val remoteDevice: BluetoothRemoteDevice
) : CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    actual var isConnected: Boolean
        get() = isConnectedAtomic.get()
        private set(value) {
            isConnectedAtomic.set(value)
        }
    private val isConnectedAtomic = AtomicBoolean(false)

    actual var isClosed: Boolean = false
        private set

    private var bluetoothGatt: BluetoothGatt? = null
    private fun getGatt() = bluetoothGatt
        ?: throw BluetoothException("There is no Bluetooth Gatt connection.")

    private val connectionStateBroadcastChannel = ConflatedBroadcastChannel<ConnectionState>()
    private val discoveryChannel = Channel<GattResult<List<BLEGattService>>>()
    private val rssiChannel = Channel<GattResult<Int>>()

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            val state = when(newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    isConnected = true
                    BLEConnectionState.STATE_CONNECTED
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    isConnected = false
                    bluetoothGatt = gatt
                    BLEConnectionState.STATE_DISCONNECTED
                }
                else -> BLEConnectionState.STATE_DISCONNECTED
            }

            connectionStateBroadcastChannel.offerSafety(ConnectionState(status, state))
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            if(status != BluetoothGatt.GATT_SUCCESS) {
                discoveryChannel.sendGattFailureResult(status)
            } else {
                discoveryChannel.sendGattSuccessResult(gatt.services.map { it.mapToGattDTO() })
            }
        }

        override fun onReadRemoteRssi(gatt: BluetoothGatt, rssi: Int, status: Int) {
            if(status != BluetoothGatt.GATT_SUCCESS) {
                rssiChannel.sendGattFailureResult(status)
            } else {
                rssiChannel.sendGattSuccessResult(rssi)
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {}
    }

    actual suspend fun connect(autoConnect: Boolean, timeoutMillis: Long) {
        if(isConnected) return
        checkCloseStatus()

        val btGatt = bluetoothGatt
        if(btGatt == null) {
            bluetoothGatt = when {
                Build.VERSION.SDK_INT >= 26 -> {
                    connectWithTimeout(timeoutMillis) {
                        remoteDevice.bluetoothDevice.connectGatt(
                            null,
                            autoConnect,
                            gattCallback,
                            BluetoothDevice.TRANSPORT_AUTO,
                            BluetoothDevice.PHY_LE_1M_MASK
                        )
                    }
                }
                Build.VERSION.SDK_INT >= 23 -> {
                    connectWithTimeout(timeoutMillis) {
                        remoteDevice.bluetoothDevice.connectGatt(
                            null,
                            autoConnect,
                            gattCallback,
                            BluetoothDevice.TRANSPORT_AUTO
                        )
                    }
                }
                else -> {
                    connectWithTimeout(timeoutMillis) {
                        remoteDevice.bluetoothDevice.connectGatt(null, autoConnect, gattCallback)
                    }
                }
            }
        } else {
            if(autoConnect) {
                connectWithTimeout(timeoutMillis) { btGatt.connect() }
            } else {
                throw BluetoothException("Can't connect second time")
            }
        }

        connectionStateBroadcastChannel.openSubscription()
            .consumeAsFlow()
            .first()
            .let {
                if(it.first != BluetoothGatt.GATT_SUCCESS) {
                    if(it.first == GATT_COMMON_ERROR) {
                        throw BluetoothConnectionErrorException("133 Error", GATT_COMMON_ERROR)
                    } else {
                        throw BluetoothException("Connection error ${it.first}.")
                    }
                }
            }
    }

    actual suspend fun discoverServices(): List<BLEGattService> {
        return gattRequest(discoveryChannel) {
            getGatt().discoverServices()
        }
    }

    actual suspend fun send(data: ByteArray) {
        if(isConnected) {
            // TODO: not implemented
        }
    }

    actual suspend fun disconnect() {
        if(!isConnected) {
            getGatt().disconnect()
        }
    }

    actual suspend fun readRssi(): Int {
        return gattRequest(rssiChannel) {
            getGatt().readRemoteRssi()
        }
    }

    actual fun close() {
        isClosed = true
        dispose()

        getGatt().disconnect()
        bluetoothGatt = null
    }

    private fun dispose() {
        discoveryChannel.close()
        connectionStateBroadcastChannel.close()
        job.cancel()
    }

    private suspend inline fun <T> connectWithTimeout(
        timeoutMillis: Long,
        crossinline connectionBlock: () -> T
    ): T {
        return try {
            withTimeout(timeoutMillis) {
                connectionBlock()
            }
        } catch (ex: TimeoutCancellationException) {
            throw BluetoothConnectionTimeoutException("Connection timed out.", timeoutMillis)
        }
    }

    private val gattRequestMutex = Mutex()

    private fun checkCloseStatus(): Boolean {
        return if (isClosed) throw BluetoothConnectionClosedException("The connection is already closed.")
        else true
    }

    private suspend inline fun <T> gattRequest(
        responseChannel: ReceiveChannel<GattResult<T>>,
        block: () -> Boolean
    ): T {
        checkCloseStatus()
        return gattRequestMutex.withLock {
            checkCloseStatus()

            if(!block()) {
                throw BluetoothException("Operation execution error.")
            }

            responseChannel.receive().let {
                when(it) {
                    is GattResult.Success -> it.data
                    is GattResult.Failure -> throw IllegalStateException()
                }
            }
        }
    }

    private fun <T> SendChannel<GattResult<T>>.sendGattSuccessResult(data: T) {
        launch {
            send(GattResult.success(data))
        }
    }

    private fun <T> SendChannel<GattResult<T>>.sendGattFailureResult(status: Int) {
        launch {
            send(GattResult.failure(status))
        }
    }

    companion object {
        private const val GATT_COMMON_ERROR = 133
    }

}
