package ru.tetraquark.bluetoothchatmpp.data

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ru.tetraquark.mpp.bluetooth.*

class RemoteDeviceRepository(
    private val bluetoothAdapter: BluetoothAdapter
) {

    private val discoveredDevices = mutableMapOf<String, BluetoothRemoteDevice>()
    private val bleConnectionsMap = mutableMapOf<BluetoothRemoteDevice, BLEGattConnection>()

    fun startDeviceDiscovery(): Flow<BluetoothRemoteDevice> {
        discoveredDevices.clear()
        return callbackFlow {
            bluetoothAdapter.startDeviceDiscovery(object : DiscoveryListener {
                override fun onDiscoveryStarted() = Unit

                override fun onDeviceFound(bluetoothRemoteDevice: BluetoothRemoteDevice) {
                    discoveredDevices[bluetoothRemoteDevice.address] = bluetoothRemoteDevice
                    offer(bluetoothRemoteDevice)
                }

                override fun onDiscoveryFinished() {
                    close()
                }
            })
            awaitClose { cancel() }
        }
    }

    fun stopDeviceDiscovery() {
        bluetoothAdapter.stopDeviceDiscovery()
    }

    suspend fun createConnection(address: String): Boolean {
        discoveredDevices[address]?.let { remoteDevice ->
            val bleConnection = bluetoothAdapter.createGattConnection(remoteDevice)
            bleConnectionsMap[remoteDevice] = bleConnection
            bleConnection.connect(false, 30_000)
            return true
        }
        return false
    }

    suspend fun closeConnection(address: String) {
        discoveredDevices[address]?.let { remoteDevice ->
            bleConnectionsMap[remoteDevice]?.disconnect()
        }
    }

    suspend fun readServicesForAddress(address: String): List<BLEGattService> {
        return discoveredDevices[address]?.let { remoteDevice ->
            bleConnectionsMap[remoteDevice]?.discoverServices()
        } ?: emptyList()
    }

}
