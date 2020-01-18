package ru.tetraquark.mpp.bluetooth

import platform.CoreBluetooth.CBPeripheral
import platform.CoreBluetooth.CBPeripheralDelegateProtocol

internal interface CBPeripheralDelegate : CBPeripheralDelegateProtocol {

    fun onPeripheralConnect(peripheral: CBPeripheral)

    fun onPeripheralDisconnect(peripheral: CBPeripheral)

    fun onPeripheralConnectionFail(peripheral: CBPeripheral, error: Int)

}
