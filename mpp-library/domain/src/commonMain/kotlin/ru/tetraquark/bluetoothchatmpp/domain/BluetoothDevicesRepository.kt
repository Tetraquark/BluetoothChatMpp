package ru.tetraquark.bluetoothchatmpp.domain

import kotlinx.coroutines.channels.ReceiveChannel
import ru.tetraquark.bluetoothchatmpp.domain.entity.BluetoothRemoteDevice

interface BluetoothDevicesRepository {

    suspend fun startDeviceDiscovery(): ReceiveChannel<BluetoothRemoteDevice>

    fun stopDeviceDiscovery()

}