package ru.tetraquark.bluetoothchatmpp.mpplibrary

class AppInjector(
    private val platformInjector: PlatformInjector
) {

    init {
        platformInjector.injectPlatform()

    }

}