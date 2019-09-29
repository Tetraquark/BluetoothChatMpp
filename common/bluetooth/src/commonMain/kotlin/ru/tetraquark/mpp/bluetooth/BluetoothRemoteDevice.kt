package ru.tetraquark.mpp.bluetooth

data class BluetoothRemoteDevice(
    val address: String,
    val name: String,
    val type: Int,
    val alias: String
)
