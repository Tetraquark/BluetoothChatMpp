package ru.tetraquark.bluetoothchatmpp.mpplibrary

import ru.tetraquark.bluetoothchatmpp.data.DataLayerFactory
import ru.tetraquark.bluetoothchatmpp.data.RemoteDeviceRepository
import ru.tetraquark.mpp.bluetooth.BluetoothAdapter

class DataLayerFactoryImpl(
    bluetoothAdapter: BluetoothAdapter
) : DataLayerFactory {

    private val remoteDeviceRepository = RemoteDeviceRepository(bluetoothAdapter)

    override fun getRemoteDeviceRepository(): RemoteDeviceRepository = remoteDeviceRepository

}
