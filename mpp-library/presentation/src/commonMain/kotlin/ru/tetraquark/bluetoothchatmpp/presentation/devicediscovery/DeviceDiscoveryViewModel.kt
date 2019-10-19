package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

class DeviceDiscoveryViewModel(
    override val eventsDispatcher: EventsDispatcher<EventListener>,
    private val deviceDiscoveryInteractor: DeviceDiscoveryInteractor
) : ViewModel(), EventsDispatcherOwner<DeviceDiscoveryViewModel.EventListener> {

    val discoveredPeers: LiveData<List<BluetoothPeer>> = deviceDiscoveryInteractor.discoveredDeviceList

    val isLoading = deviceDiscoveryInteractor.isLoading

    init {
        coroutineScope.launch {
            deviceDiscoveryInteractor.startDiscovery()
        }
    }

    interface EventListener {
        fun showError(message: String)
    }

}
