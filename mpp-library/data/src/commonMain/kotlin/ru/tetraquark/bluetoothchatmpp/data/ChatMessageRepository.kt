package ru.tetraquark.bluetoothchatmpp.data

import ru.tetraquark.mpp.bluetooth.BluetoothAdapter
import ru.tetraquark.mpp.bluetooth.BLEGattConnection
import ru.tetraquark.mpp.bluetooth.BluetoothRemoteDevice
import ru.tetraquark.mpp.bluetooth.ConnectionListener

class ChatMessageRepository(
    private val bluetoothAdapter: BluetoothAdapter
) {

    private val openConnectionsMap = mutableMapOf<BluetoothRemoteDevice, BLEGattConnection>()
    //private val connectionObserversMap = mutableMapOf<BluetoothRemoteDevice, BluetoothConnectionObserver>()

    suspend fun connectToDevice(device: BluetoothRemoteDevice) {
        // TODO("not implemented")
        //val blConnection = bluetoothAdapter.connectTo(device)
    }

}