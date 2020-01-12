package ru.tetraquark.mpp.bluetooth

actual class BluetoothAdapter(
    actual val uuid: String,
    actual val messagesBufferLength: Int = 1024
) {

    actual fun isAvailable(): Boolean {
        return true
    }

    actual fun isEnabled(): Boolean {
        return true
    }

    actual fun makeDeviceVisible(seconds: Int) {
    }

    actual fun startDeviceDiscovery(listener: DiscoveryListener) {
    }

    actual fun stopDeviceDiscovery() {
    }

    actual fun getDeviceName(): String = ""

    actual fun getDeviceAddress(): String = ""

    actual fun connectTo(bluetoothRemoteDevice: BluetoothRemoteDevice): BLEGattConnection {
        TODO("")
    }

}
