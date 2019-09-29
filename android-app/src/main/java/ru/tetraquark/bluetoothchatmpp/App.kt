package ru.tetraquark.bluetoothchatmpp

import android.app.Application
import ru.tetraquark.bluetoothchatmpp.mpplibrary.AppInjector
import ru.tetraquark.bluetoothchatmpp.mpplibrary.PlatformInjector

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val appInjector = AppInjector(PlatformInjector())
    }
}
