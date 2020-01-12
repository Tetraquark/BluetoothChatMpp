package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

import dev.icerock.moko.mvvm.livedata.LiveData

interface DeviceDiscoveryInteractor {

    val discoveredDeviceList: LiveData<List<BluetoothPeer>>

    val isLoading: LiveData<Boolean>

    suspend fun startDiscovery()

    fun stopDiscovery()

    suspend fun connectToDevice(index: Int)

}
