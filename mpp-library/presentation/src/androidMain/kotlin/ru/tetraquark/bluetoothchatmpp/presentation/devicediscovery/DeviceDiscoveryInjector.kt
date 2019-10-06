package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

import dev.icerock.moko.mvvm.ViewModelFactory

interface DeviceDiscoveryInjector {

    fun injectViewModelFactory(factory: ViewModelFactory)

}