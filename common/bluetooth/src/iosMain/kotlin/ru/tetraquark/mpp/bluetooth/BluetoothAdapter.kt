package ru.tetraquark.mpp.bluetooth

import platform.CoreBluetooth.*
import platform.Foundation.*
import platform.darwin.NSObject

actual class BluetoothAdapter(
    actual val uuid: String
) {

    private val centralManager = CBCentralManager(CBManagerListener(), null)

    private var discoveryListener: DiscoveryListener? = null

    private val cbManagerHandler = AdapterCBManagerHandler()
    private val peripheralListenerSet = mutableSetOf<CBPeripheralDelegate>()

    actual fun getDeviceName(): String {
        // TODO: not implemented
        return ""
    }

    actual fun getDeviceAddress(): String {
        // TODO: not implemented
        return ""
    }

    actual fun isAvailable(): Boolean {
        // TODO: not implemented
        return true
    }

    actual fun isEnabled(): Boolean {
        // TODO: not implemented
        centralManager.state
        return true
    }

    actual fun isDiscovering(): Boolean {
        return centralManager.isScanning
    }

    actual fun makeDeviceVisible(seconds: Int) {
        // TODO: not implemented
    }

    actual fun startDeviceDiscovery(listener: DiscoveryListener) {
        discoveryListener = listener
    }

    actual fun stopDeviceDiscovery() {
        discoveryListener = null
        centralManager.stopScan()
    }

    actual fun createGattConnection(bluetoothRemoteDevice: BluetoothRemoteDevice): BLEGattConnection {
        return BLEGattConnection(bluetoothRemoteDevice, cbManagerHandler)
    }

    internal inner class AdapterCBManagerHandler : CBManagerHandler {

        override fun connectToPeripheral(peripheral: CBPeripheral) {
            centralManager.connectPeripheral(peripheral, null)
        }

        override fun disconnectFromPeripheral(peripheral: CBPeripheral) {
            centralManager.cancelPeripheralConnection(peripheral)
        }

        override fun addPeripheralListener(listener: CBPeripheralDelegate) {
            peripheralListenerSet.add(listener)
        }

        override fun removePeripheralListener(listener: CBPeripheralDelegate) {
            peripheralListenerSet.remove(listener)
        }

    }

    inner class CBManagerListener : NSObject(), CBCentralManagerDelegateProtocol {

        override fun centralManagerDidUpdateState(central: CBCentralManager) {
            when (central.state) {
                CBManagerStateUnknown -> {}
                CBManagerStateResetting -> {}
                CBManagerStateUnsupported -> {}
                CBManagerStateUnauthorized -> {}
                CBManagerStatePoweredOff -> {}
                CBManagerStatePoweredOn -> {}
                else -> {}
            }
        }

        override fun centralManager(
            central: CBCentralManager,
            didDiscoverPeripheral: CBPeripheral,
            advertisementData: Map<Any?, *>,
            RSSI: NSNumber
        ) {
            if (central.isScanning) {
                discoveryListener?.onDeviceFound(BluetoothRemoteDevice(didDiscoverPeripheral))
            }
        }

        override fun centralManager(central: CBCentralManager, didConnectPeripheral: CBPeripheral) {
            peripheralListenerSet.forEach { it.onPeripheralConnect(didConnectPeripheral) }
        }

        override fun centralManager(
            central: CBCentralManager,
            didDisconnectPeripheral: CBPeripheral,
            error: NSError?
        ) {
            peripheralListenerSet.forEach { it.onPeripheralDisconnect(didDisconnectPeripheral) }
        }

        /*
        override fun centralManager(
            central: CBCentralManager,
            didFailToConnectPeripheral: CBPeripheral,
            error: NSError?
        ) {
            super.centralManager(central, didFailToConnectPeripheral, error)
        }
        */

    }

}
