package ru.tetraquark.mpp.bluetooth

expect class BluetoothConnection {

    val remoteDevice: BluetoothRemoteDevice

    fun isConnected(): Boolean

    fun send(data: ByteArray)

    fun close()

    fun addConnectionListener(listener: ConnectionListener)

    fun removeConnectionListener(listener: ConnectionListener)

}
