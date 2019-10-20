package ru.tetraquark.bluetoothchatmpp.domain

interface DomainFactory {

    fun getDiscoveryBluetoothDevicesInteractor() : DiscoveryBluetoothDevicesInteractor

}