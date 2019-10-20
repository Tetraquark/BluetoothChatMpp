package ru.tetraquark.bluetoothchatmpp.mpplibrary

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tetraquark.bluetoothchatmpp.data.DataLayerFactory
import ru.tetraquark.bluetoothchatmpp.data.RemoteDeviceRepository
import ru.tetraquark.bluetoothchatmpp.domain.BluetoothDevicesRepository
import ru.tetraquark.bluetoothchatmpp.domain.DiscoveryBluetoothDevicesInteractor
import ru.tetraquark.bluetoothchatmpp.domain.DomainFactory
import ru.tetraquark.bluetoothchatmpp.domain.entity.BluetoothRemoteDevice

class DomainFactoryImpl(
    private val dataLayerInjector: DataLayerFactory
) : DomainFactory {

    override fun getDiscoveryBluetoothDevicesInteractor(): DiscoveryBluetoothDevicesInteractor {
        return DiscoveryBluetoothDevicesInteractor(
            bluetoothDevicesRepository = createBluetoothDevicesRepository(dataLayerInjector.getRemoteDeviceRepository())
        )
    }

    /**
     * Mapping data repository -> domain repository interface.
     */
    private fun createBluetoothDevicesRepository(
        remoteDeviceRepository: RemoteDeviceRepository
    ): BluetoothDevicesRepository {
        return object : BluetoothDevicesRepository {
            override suspend fun startDeviceDiscovery(): Flow<BluetoothRemoteDevice> {
                return remoteDeviceRepository.startDeviceDiscovery().map {
                    BluetoothRemoteDevice(
                        address = it.address,
                        name = it.name,
                        type = it.type
                    )
                }
            }

            override fun stopDeviceDiscovery() {
                remoteDeviceRepository.stopDeviceDiscovery()
            }
        }
    }

}