package ru.tetraquark.mpp.bluetooth

import android.bluetooth.BluetoothGattService

actual class BLEGattService(
    actual val uuid: UUID
) {
    actual var characteristic: List<BLEGattCharacteristic> = emptyList()
        internal set
}

fun BluetoothGattService.mapToGattDTO(): BLEGattService {
    return BLEGattService(
        uuid = this.uuid.mapToDTO()
    ).apply {
        characteristic = characteristics.map { it.mapToGattDTO(this) }
    }
}
