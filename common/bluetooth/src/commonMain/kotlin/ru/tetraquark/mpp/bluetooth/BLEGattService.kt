package ru.tetraquark.mpp.bluetooth

expect class BLEGattService {

    val uuid: UUID
    var characteristic: List<BLEGattCharacteristic>

}

