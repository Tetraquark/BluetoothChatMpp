package ru.tetraquark.mpp.bluetooth

interface ConnectionListener {

    fun onConnected()

    fun onDisconnected()

    fun onReceived(data: ByteArray)

    fun onClose()

    fun onError(exception: Throwable)

}