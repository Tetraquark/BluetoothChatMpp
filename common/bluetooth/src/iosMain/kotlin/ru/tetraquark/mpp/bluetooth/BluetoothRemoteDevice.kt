package ru.tetraquark.mpp.bluetooth

import platform.CoreBluetooth.CBPeripheral

actual class BluetoothRemoteDevice constructor(
    val peripheral: CBPeripheral
) {

    /**
     * There is no access to mac-address of the peripheral device in iOS.
     */
    actual val address: String = peripheral.identifier.UUIDString

    actual val name: String? = peripheral.name

    /**
     * iOS supports only BLE protocol.
     */
    actual val type: BluetoothRemoteDeviceType = BluetoothRemoteDeviceType.LE

}
