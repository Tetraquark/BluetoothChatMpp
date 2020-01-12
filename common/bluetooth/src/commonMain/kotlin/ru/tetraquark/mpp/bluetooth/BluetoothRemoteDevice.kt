package ru.tetraquark.mpp.bluetooth

expect class BluetoothRemoteDevice {
    val address: String
    val name: String?
    val type: BluetoothRemoteDeviceType
}
