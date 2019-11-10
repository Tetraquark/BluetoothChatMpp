package ru.tetraquark.bluetoothchatmpp.mpplibrary

import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.widgets.core.WidgetScope
import ru.tetraquark.bluetoothchatmpp.domain.DiscoveryBluetoothDevicesInteractor
import ru.tetraquark.bluetoothchatmpp.presentation.PresentationFactory
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.BluetoothPeer
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryInteractor
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryViewModel
import ru.tetraquark.bluetoothchatmpp.domain.DomainFactory
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryUnitFactoryImpl

actual class PresentationFactoryImpl(
    private val domainFactory: DomainFactory
): PresentationFactory {

    override fun createDeviceDiscoveryViewModel(): DeviceDiscoveryViewModel {
        return DeviceDiscoveryViewModel(
            eventsDispatcher = eventsDispatcherOnMain(),
            deviceDiscoveryInteractor = createDeviceDiscoveryInteractor(domainFactory.getDiscoveryBluetoothDevicesInteractor()),
            unitFactory = DeviceDiscoveryUnitFactoryImpl(),
            widgetScope = WidgetScope()
        )
    }

    /**
     * Implements presentation interactor interface using domain interactor implementation.
     * Mapping domain interactor -> presentation interactor interface.
     */
    private fun createDeviceDiscoveryInteractor(
        discoveryBluetoothDevicesInteractor: DiscoveryBluetoothDevicesInteractor
    ): DeviceDiscoveryInteractor {
        return object : DeviceDiscoveryInteractor {
            override val isLoading: LiveData<Boolean> = discoveryBluetoothDevicesInteractor.isLoading

            override val discoveredDeviceList: LiveData<List<BluetoothPeer>> =
                discoveryBluetoothDevicesInteractor.discoveredDeviceList.map { list ->
                    list.map {
                    val name = if(it.name.isBlank()) {
                        "No name"
                    } else {
                        it.name
                    }
                    BluetoothPeer(name, it.address)
                }
            }

            override suspend fun startDiscovery() {
                discoveryBluetoothDevicesInteractor.startDevicesDiscovery()
            }

            override fun stopDiscovery() {
                discoveryBluetoothDevicesInteractor.stopDevicesDiscovery()
            }
        }
    }
}