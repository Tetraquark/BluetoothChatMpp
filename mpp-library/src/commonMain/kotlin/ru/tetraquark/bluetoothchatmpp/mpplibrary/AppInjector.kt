package ru.tetraquark.bluetoothchatmpp.mpplibrary

class AppInjector(
    platformInjector: PlatformInjector
) {

    init {
        platformInjector.injectPlatform()
    }

}
