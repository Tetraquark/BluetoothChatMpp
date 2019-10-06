package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

import dev.icerock.moko.mvvm.livedata.LiveData

interface DeviceDiscoveryInteractor {

    val discoveredDeviceList: LiveData<List<BluetoothPeer>>

    suspend fun startDiscovery()

    fun stopDiscovery()

}
