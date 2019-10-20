package ru.tetraquark.mpp.bluetooth

interface ConnectionListener {

    fun onReceived(data: ByteArray)

    fun onClose()

    fun onError(exception: Throwable)

}