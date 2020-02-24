package ru.tetraquark.bluetoothchatmpp.mpplibrary

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tetraquark.bluetoothchatmpp.data.DataLayerFactory
import ru.tetraquark.bluetoothchatmpp.data.RemoteDeviceRepository
import ru.tetraquark.bluetoothchatmpp.domain.BluetoothDevicesRepository
import ru.tetraquark.bluetoothchatmpp.domain.DiscoveryBluetoothDevicesInteractor
import ru.tetraquark.bluetoothchatmpp.domain.DomainFactory
import ru.tetraquark.bluetoothchatmpp.domain.entity.BLECharacteristic
import ru.tetraquark.bluetoothchatmpp.domain.entity.BLEService
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
                        name = it.name.orEmpty(),
                        type = it.type.code
                    )
                }
            }

            override fun stopDeviceDiscovery() {
                remoteDeviceRepository.stopDeviceDiscovery()
            }

            override suspend fun createBLEConnection(remoteDevice: BluetoothRemoteDevice) {
                remoteDeviceRepository.createConnection(remoteDevice.address)
            }

            override suspend fun closeBLEConnection(remoteDevice: BluetoothRemoteDevice) {
                remoteDeviceRepository.closeConnection(remoteDevice.address)
            }

            override suspend fun discoverServices(remoteDevice: BluetoothRemoteDevice): List<BLEService> {
                return remoteDeviceRepository.readServicesForAddress(remoteDevice.address).map {
                    BLEService(
                        uuid = it.uuid.asString(),
                        characteristics = it.characteristic.map { character ->
                            BLECharacteristic(
                                uuid = character.uuid.asString(),
                                data = character.value
                            )
                        }
                    )
                }
            }

        }
    }

}