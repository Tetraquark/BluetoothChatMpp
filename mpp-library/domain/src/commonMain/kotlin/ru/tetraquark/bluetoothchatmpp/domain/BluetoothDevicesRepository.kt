package ru.tetraquark.bluetoothchatmpp.domain

import kotlinx.coroutines.flow.Flow
import ru.tetraquark.bluetoothchatmpp.domain.entity.BLEService
import ru.tetraquark.bluetoothchatmpp.domain.entity.BluetoothRemoteDevice

interface BluetoothDevicesRepository {

    suspend fun startDeviceDiscovery(): Flow<BluetoothRemoteDevice>

    fun stopDeviceDiscovery()

    suspend fun createBLEConnection(remoteDevice: BluetoothRemoteDevice)

    suspend fun closeBLEConnection(remoteDevice: BluetoothRemoteDevice)

    suspend fun discoverServices(remoteDevice: BluetoothRemoteDevice): List<BLEService>

}
