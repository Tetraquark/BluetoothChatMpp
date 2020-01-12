package ru.tetraquark.mpp.bluetooth

expect class BLEGattCharacteristic {

    val service: BLEGattService

    val uuid: UUID

    val value: ByteArray

}
