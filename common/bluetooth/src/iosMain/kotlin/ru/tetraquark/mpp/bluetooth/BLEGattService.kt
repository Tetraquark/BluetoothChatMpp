package ru.tetraquark.mpp.bluetooth

import platform.CoreBluetooth.CBCharacteristic
import platform.CoreBluetooth.CBService

actual class BLEGattService(
    actual val uuid: UUID
) {
    actual var characteristic: List<BLEGattCharacteristic> = emptyList()
        internal set
}

fun CBService.mapToGattDTO() = BLEGattService(
    uuid = UUID.mapToDTO()
).apply {
    characteristic = characteristics
        ?.asSequence()
        ?.map { it as? CBCharacteristic }
        ?.filterNotNull()
        ?.map { it.mapToDTO(this) }
        ?.toList()
        .orEmpty()
}
