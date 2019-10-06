package ru.tetraquark.bluetoothchatmpp.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryInteractor
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryViewModel

fun createDeviceDiscoveryViewModel(
    eventsDispatcher: EventsDispatcher<DeviceDiscoveryViewModel.EventListener>,
    deviceDiscoveryInteractor: DeviceDiscoveryInteractor
): DeviceDiscoveryViewModel {
    return DeviceDiscoveryViewModel(
        eventsDispatcher = eventsDispatcher,
        deviceDiscoveryInteractor = deviceDiscoveryInteractor
    )
}