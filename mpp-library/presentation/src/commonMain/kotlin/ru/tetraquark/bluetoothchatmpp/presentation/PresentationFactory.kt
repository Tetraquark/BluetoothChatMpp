package ru.tetraquark.bluetoothchatmpp.presentation

import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryViewModel

interface PresentationFactory {

    fun createDeviceDiscoveryViewModel(): DeviceDiscoveryViewModel

}
