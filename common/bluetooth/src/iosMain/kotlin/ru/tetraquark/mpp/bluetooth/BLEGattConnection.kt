package ru.tetraquark.mpp.bluetooth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import platform.CoreBluetooth.*
import platform.Foundation.*
import platform.darwin.NSObject

actual class BLEGattConnection internal constructor(
    actual val remoteDevice: BluetoothRemoteDevice,
    private val cbManagerHandler: CBManagerHandler
) : CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = MainDispatcher + job

    private val peripheralListener = CBPeripheralListener()

    actual var isConnected: Boolean = false
        private set

    actual var isClosed: Boolean = false
        private set

    init {
        cbManagerHandler.addPeripheralListener(peripheralListener)
    }

    actual suspend fun connect(autoConnect: Boolean) {

    }

    actual suspend fun discoverServices(): List<BLEGattService> {
        return remoteDevice.peripheral.services?.map {
            (it as CBService).mapToGattDTO()
        }.orEmpty()
    }

    actual suspend fun send(data: ByteArray) {

    }

    actual suspend fun disconnect() {

    }

    actual fun close() {
        cbManagerHandler.removePeripheralListener(peripheralListener)
    }

    inner class CBPeripheralListener : NSObject(), CBPeripheralDelegate {

        override fun onPeripheralConnect(peripheral: CBPeripheral) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onPeripheralDisconnect(peripheral: CBPeripheral) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onPeripheralConnectionFail(peripheral: CBPeripheral, error: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

}
