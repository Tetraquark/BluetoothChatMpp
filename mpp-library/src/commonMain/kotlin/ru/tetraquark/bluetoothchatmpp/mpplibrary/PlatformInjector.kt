package ru.tetraquark.bluetoothchatmpp.mpplibrary

expect class PlatformInjector {

    val appUuid: String

    fun injectPlatform()
}