package ru.tetraquark.mpp.bluetooth

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.CoreBluetooth.CBCharacteristic
import platform.posix.memcpy

actual class BLEGattCharacteristic(
    actual val service: BLEGattService,
    actual val uuid: UUID,
    actual val value: ByteArray
)

fun CBCharacteristic.mapToDTO(service: BLEGattService) = BLEGattCharacteristic(
    service = service,
    uuid = UUID.mapToDTO(),
    value = value?.let { data ->
        ByteArray(data.length.toInt()).apply {
            usePinned {
                memcpy(it.addressOf(0), data.bytes, data.length)
            }
        }
    } ?: ByteArray(0)
)
