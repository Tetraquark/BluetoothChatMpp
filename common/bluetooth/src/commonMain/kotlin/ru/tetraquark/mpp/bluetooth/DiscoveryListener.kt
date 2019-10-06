package ru.tetraquark.mpp.bluetooth

interface DiscoveryListener {

    fun onDiscoveryStarted()

    fun onDeviceFound(bluetoothRemoteDevice: BluetoothRemoteDevice)

    fun onDiscoveryFinished()

}