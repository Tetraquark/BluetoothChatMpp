package ru.tetraquark.mpp.bluetooth

expect class BluetoothAdapter {

    fun isAvailable(): Boolean

    fun isEnabled(): Boolean

    fun startDeviceDiscovery(listener: DiscoveryListener)

    fun stopDeviceDiscovery()

    fun makeDeviceVisible(seconds: Int = 60)

    fun getDeviceName(): String

    fun getDeviceAddress(): String

}
