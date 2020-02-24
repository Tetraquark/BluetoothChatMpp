package ru.tetraquark.mpp.bluetooth

expect class BluetoothAdapter {

    val uuid: String

    fun isAvailable(): Boolean

    fun isEnabled(): Boolean

    fun isDiscovering(): Boolean

    fun startDeviceDiscovery(listener: DiscoveryListener)

    fun stopDeviceDiscovery()

    fun makeDeviceVisible(seconds: Int)

    fun getDeviceName(): String

    fun getDeviceAddress(): String

    /**
     * Creates Gatt connection with remote device [bluetoothRemoteDevice].
     *
     * @throws BluetoothException if [bluetoothRemoteDevice] doesn't support BLE.
     */
    fun createGattConnection(bluetoothRemoteDevice: BluetoothRemoteDevice): BLEGattConnection

}
