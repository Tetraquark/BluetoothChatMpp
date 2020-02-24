package ru.tetraquark.mpp.bluetooth

open class BluetoothException(message: String) : Exception(message)

class BluetoothConnectionTimeoutException(message: String, val time: Long = 0) : BluetoothException(message)

class BluetoothConnectionClosedException(message: String) : BluetoothException(message)

class BluetoothConnectionErrorException(message: String, val errorCode: Int) : BluetoothException(message)

