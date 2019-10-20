package ru.tetraquark.bluetoothchatmpp.data

interface DataLayerFactory {

    fun getRemoteDeviceRepository(): RemoteDeviceRepository

}
