package ru.tetraquark.mpp.bluetooth

import android.bluetooth.BluetoothDevice

actual class BluetoothRemoteDevice constructor(
    val bluetoothDevice: BluetoothDevice
) {

    actual val address: String = bluetoothDevice.address
    actual val name: String? = bluetoothDevice.name
    actual val type: BluetoothRemoteDeviceType = BluetoothRemoteDeviceType.getByCode(bluetoothDevice.type)

}
