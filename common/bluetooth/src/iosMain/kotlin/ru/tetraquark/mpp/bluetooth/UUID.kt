package ru.tetraquark.mpp.bluetooth

import platform.CoreBluetooth.CBUUID

actual class UUID(val nativeUuid: CBUUID) {

    actual fun asString(): String {
        return nativeUuid.UUIDString
    }

}

fun CBUUID.mapToDTO() = UUID(this)
