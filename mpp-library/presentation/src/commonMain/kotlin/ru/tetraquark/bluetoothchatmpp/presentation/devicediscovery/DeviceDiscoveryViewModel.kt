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
        viewModelScope.launch {
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

    fun onStopScanClick() {
        deviceDiscoveryInteractor.stopDiscovery()
    }

    fun onBluetoothDeviceClick(index: Int) {
        viewModelScope.launch {
            try {
                deviceDiscoveryInteractor.connectToDevice(index)
                val rssi = deviceDiscoveryInteractor.readConnectionRssi()
                println("RSSI: $rssi")
                val services = deviceDiscoveryInteractor.getServices(index)
                println("Services: $services")
                eventsDispatcher.dispatchEvent { showError("Success connect") }
            } catch (gattException: GattConnectionException) {
                eventsDispatcher.dispatchEvent { showError(gattException.message ?: "Unknown error") }
            } catch (error: Throwable) {
                eventsDispatcher.dispatchEvent { showError(error.message ?: "Unknown error") }
            }
        }
    }

    interface EventListener {
        fun showError(message: String)
    }

}
