package ru.tetraquark.mpp.bluetooth.extensions

import kotlinx.coroutines.channels.SendChannel

//TODO: Check https://github.com/Kotlin/kotlinx.coroutines/issues/974.
internal fun <T> SendChannel<T>.offerSafety(value: T): Boolean {
    return kotlin.runCatching { offer(value) }.getOrDefault(false)
}
