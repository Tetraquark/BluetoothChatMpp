package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

data class GattCharacteristic(
    val uuid: String,
    val data: ByteArray
)
