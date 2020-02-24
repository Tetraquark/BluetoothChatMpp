package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

data class GattService(
    val uuid: String,
    val characteristics: List<GattCharacteristic>
)
