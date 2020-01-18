package ru.tetraquark.mpp.bluetooth

expect class BluetoothAdapter {

    val uuid: String

    fun isAvailable(): Boolean

    fun isEnabled(): Boolean

    fun isDiscovering(): Boolean

    fun startDeviceDiscovery(listener: DiscoveryListener)

    fun stopDeviceDiscovery()

    fun makeDeviceVisible(seconds: Int = 60)

    fun getDeviceName(): String

    fun getDeviceAddress(): String

    fun createGattConnection(bluetoothRemoteDevice: BluetoothRemoteDevice): BLEGattConnection

}
