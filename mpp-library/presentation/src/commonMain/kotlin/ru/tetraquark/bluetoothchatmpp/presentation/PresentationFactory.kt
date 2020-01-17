package ru.tetraquark.bluetoothchatmpp.presentation

import dev.icerock.moko.permissions.PermissionsController
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryViewModel

interface PresentationFactory {

    fun createDeviceDiscoveryViewModel(permissionsController: PermissionsController): DeviceDiscoveryViewModel

}
