package ru.tetraquark.mpp.bluetooth

import android.bluetooth.BluetoothGattCharacteristic

actual class BLEGattCharacteristic(
    actual val service: BLEGattService,
    actual val uuid: UUID,
    actual val value: ByteArray
)

fun BluetoothGattCharacteristic.mapToGattDTO(service: BLEGattService): BLEGattCharacteristic {
    return BLEGattCharacteristic(
        service = service,
        uuid = this.uuid.mapToDTO(),
        value = this.value ?: ByteArray(0)
    )
}
