package ru.tetraquark.bluetoothchatmpp.mpplibrary

import ru.tetraquark.bluetoothchatmpp.presentation.PresentationFactory
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryViewModel
import ru.tetraquark.bluetoothchatmpp.domain.DomainFactory

actual class PresentationFactoryImpl(
    private val domainFactory: DomainFactory
): PresentationFactory {

    override fun createDeviceDiscoveryViewModel(): DeviceDiscoveryViewModel {
        TODO("")
    }

}
