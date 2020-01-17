package ru.tetraquark.mpp.bluetooth

import kotlin.coroutines.CoroutineContext

actual class BLEGattConnection(
    actual val remoteDevice: BluetoothRemoteDevice
) : CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    actual fun isConnected(): Boolean = true

    actual fun send(data: ByteArray) {}

    actual fun close() {}

    actual fun addConnectionListener(listener: ConnectionListener) {}

    actual fun removeConnectionListener(listener: ConnectionListener) {}
}
