package ru.tetraquark.mpp.bluetooth

import android.bluetooth.*
import android.os.Build
import com.github.aakira.napier.Napier
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext

actual class BLEGattConnection(
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

    private var bluetoothGatt: BluetoothGatt? = null
    private fun getGatt() = bluetoothGatt
        ?: throw BluetoothException("There is no Bluetooth Gatt connection.")


    private val connectionChannel = Channel<GattResult<BLEConnectionState>>()
    private val discoveryChannel = Channel<GattResult<List<BLEGattService>>>()

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            Napier.d("{DEBUG} onConnectionStateChange: status $status, state $newState")
            if(status != BluetoothGatt.GATT_SUCCESS) {
                connectionChannel.sendGattFailureResult(status)
                return
            }

            when(newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    isConnected = true
                    gatt.discoverServices()
                    connectionChannel.sendGattSuccessResult(BLEConnectionState.STATE_CONNECTED)
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    isConnected = false
                    connectionChannel.sendGattSuccessResult(BLEConnectionState.STATE_DISCONNECTED)
                    bluetoothGatt = null
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            if(status != BluetoothGatt.GATT_SUCCESS) {
                discoveryChannel.sendGattFailureResult(status)
                return
            }

            discoveryChannel.sendGattSuccessResult(gatt.services.map { it.mapToGattDTO() })

            //val bluetoothDesc = characteristic.getDescriptor()
            //bluetoothDesc.setValue()
            //gatt.writeDescriptor(bluetoothDesc)
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {}
    }

    actual suspend fun connect(autoConnect: Boolean) {
        gattRequest(connectionChannel) {
            bluetoothGatt = remoteDevice.bluetoothDevice.connectGatt(null, autoConnect, gattCallback)
        }
    }

    actual suspend fun discoverServices(): List<BLEGattService> {
        return gattRequest(discoveryChannel) {
            getGatt().discoverServices()
        }
    }

    actual suspend fun send(data: ByteArray) {
        if(isConnected) {

        }
    }

    actual suspend fun disconnect() {
        if(!isConnected) {
            getGatt().disconnect()
        }
    }

    actual fun close() {
        isClosed = true
        dispose()

        getGatt().disconnect()
        bluetoothGatt = null
    }

    private fun dispose() {
        job.cancel()
    }

    private val gattRequestMutex = Mutex()

    private suspend inline fun <T> gattRequest(
        responseChannel: ReceiveChannel<GattResult<T>>,
        block: () -> Unit
    ): T {
        checkCloseStatus()
        return gattRequestMutex.withLock {
            checkCloseStatus()
            block() // TODO: add check for request result (some of them returns boolean)

            responseChannel.receive().let {
                when(it) {
                    is GattResult.Success -> it.data
                    is GattResult.Failure -> throw IllegalStateException()
                }
            }
        }
    }

    private fun checkCloseStatus(): Boolean {
        return if(isClosed) throw BluetoothException("The connection is already closed.")
        else true
    }

    sealed class GattResult<out T> {

        data class Success<out T> internal constructor(val data: T): GattResult<T>()
        data class Failure internal constructor(val status: Int): GattResult<Nothing>()

        companion object {
            fun <T> success(value: T) = Success(value)

            fun failure(status: Int) = Failure(status)
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

}
