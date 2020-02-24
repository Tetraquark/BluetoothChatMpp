package ru.tetraquark.bluetoothchatmpp.domain.entity

data class BLEService(
    val uuid: String,
    val characteristics: List<BLECharacteristic>
)
