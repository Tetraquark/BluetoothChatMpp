package ru.tetraquark.mpp.bluetooth

actual class BluetoothConnection(
    actual val remoteDevice: BluetoothRemoteDevice
) {
    actual fun isConnected(): Boolean = true

    actual fun send(data: ByteArray) {}

    actual fun close() {}

    actual fun addConnectionListener(listener: ConnectionListener) {}

    actual fun removeConnectionListener(listener: ConnectionListener) {}
}
