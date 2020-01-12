package ru.tetraquark.mpp.bluetooth

import java.util.UUID as JvmUUID

actual class UUID(val uuid: JvmUUID) {

    actual fun asString(): String = uuid.toString()

}

fun java.util.UUID.mapToDTO() = UUID(this)
