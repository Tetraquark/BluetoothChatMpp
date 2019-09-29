package ru.tetraquark.mpp.bluetooth

expect class BluetoothAdapter {

    fun isAvailable(): Boolean

    fun isEnabled(): Boolean

    fun startDeviceDiscovery()

    fun stopDeviceDiscovery()

    fun getDeviceName(): String

    fun getDeviceAddress(): String

}
