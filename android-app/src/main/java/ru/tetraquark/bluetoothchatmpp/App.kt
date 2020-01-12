package ru.tetraquark.bluetoothchatmpp

import android.app.Application
import com.github.aakira.napier.DebugAntilog
import com.github.aakira.napier.Napier
import ru.tetraquark.bluetoothchatmpp.mpplibrary.AppInjector
import ru.tetraquark.bluetoothchatmpp.mpplibrary.PlatformInjector

class App : Application() {

    companion object {
        const val APP_UUID = "9cdc912c-ea58-4135-944d-d0bf7f07353d"
    }

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        val appInjector = AppInjector(PlatformInjector(APP_UUID, this))
    }
}
