package ru.tetraquark.bluetoothchatmpp.mpplibrary

expect class PlatformInjector {

    val appUuid: String

    internal fun injectPlatform()
}
