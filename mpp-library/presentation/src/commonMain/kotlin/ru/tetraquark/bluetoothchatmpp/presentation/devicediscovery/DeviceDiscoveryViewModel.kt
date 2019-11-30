package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch

class DeviceDiscoveryViewModel(
    override val eventsDispatcher: EventsDispatcher<EventListener>,
    val permissionsController: PermissionsController,
    private val deviceDiscoveryInteractor: DeviceDiscoveryInteractor
) : ViewModel(), EventsDispatcherOwner<DeviceDiscoveryViewModel.EventListener> {

    val discoveredPeers: LiveData<List<BluetoothPeer>> = deviceDiscoveryInteractor.discoveredDeviceList

    val isLoading = deviceDiscoveryInteractor.isLoading

    init {
        coroutineScope.launch {
            try {
                permissionsController.providePermission(Permission.COARSE_LOCATION)
                deviceDiscoveryInteractor.startDiscovery()
            } catch (deniedException: DeniedException) {
                // TODO:
            } catch (error: Throwable) {
                // TODO:
            }
        }
    }

    interface EventListener {
        fun showError(message: String)
    }

}
