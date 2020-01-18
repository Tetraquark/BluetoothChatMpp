package ru.tetraquark.mpp.bluetooth

import platform.CoreBluetooth.CBPeripheral

internal interface CBManagerHandler {

    fun connectToPeripheral(peripheral: CBPeripheral)

    fun disconnectFromPeripheral(peripheral: CBPeripheral)

    fun addPeripheralListener(listener: CBPeripheralDelegate)

    fun removePeripheralListener(listener: CBPeripheralDelegate)

}
