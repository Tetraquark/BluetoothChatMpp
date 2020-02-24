package ru.tetraquark.bluetoothchatmpp.mpplibrary

import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.permissions.PermissionsController
import ru.tetraquark.bluetoothchatmpp.domain.DiscoveryBluetoothDevicesInteractor
import ru.tetraquark.bluetoothchatmpp.presentation.PresentationFactory
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.BluetoothPeer
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryInteractor
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryViewModel
import ru.tetraquark.bluetoothchatmpp.domain.DomainFactory
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.GattCharacteristic
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.GattConnectionException
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.GattService
import ru.tetraquark.mpp.bluetooth.BluetoothConnectionErrorException

actual class PresentationFactoryImpl(
    private val domainFactory: DomainFactory
): PresentationFactory {

    override fun createDeviceDiscoveryViewModel(permissionsController: PermissionsController): DeviceDiscoveryViewModel {
        return DeviceDiscoveryViewModel(
            eventsDispatcher = eventsDispatcherOnMain(),
            permissionsController = permissionsController,
            deviceDiscoveryInteractor = createDeviceDiscoveryInteractor(domainFactory.getDiscoveryBluetoothDevicesInteractor())
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

            override suspend fun connectToDevice(index: Int) {
                try {
                    discoveryBluetoothDevicesInteractor.connectToDevice(index)
                } catch (bleGattError: BluetoothConnectionErrorException) {
                    throw GattConnectionException(bleGattError.message)
                }
            }

            override suspend fun disconnectFromDevice(index: Int) {
                discoveryBluetoothDevicesInteractor.disconnectFromDevice(index)
            }

            override suspend fun getServices(index: Int): List<GattService> {
                return discoveryBluetoothDevicesInteractor.discoverServices().map {
                    GattService(
                        it.uuid,
                        it.characteristics.map { bleChar ->
                            GattCharacteristic(
                                bleChar.uuid,
                                bleChar.data
                            )
                        }
                    )
                }
            }
        }
    }
}