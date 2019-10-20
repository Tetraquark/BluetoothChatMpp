package ru.tetraquark.bluetoothchatmpp.data

import kotlinx.coroutines.flow.Flow
import ru.tetraquark.mpp.bluetooth.BluetoothAdapter
import ru.tetraquark.mpp.bluetooth.BluetoothConnection
import ru.tetraquark.mpp.bluetooth.BluetoothRemoteDevice
import ru.tetraquark.mpp.bluetooth.ConnectionListener

class ChatMessageRepository(
    private val bluetoothAdapter: BluetoothAdapter
) {

    private val openConnectionsMap = mutableMapOf<BluetoothRemoteDevice, BluetoothConnection>()
    private val connectionObserversMap = mutableMapOf<BluetoothRemoteDevice, BluetoothConnectionObserver>()

    suspend fun connectToDevice(device: BluetoothRemoteDevice) {
        // TODO("not implemented")
        val blConnection = bluetoothAdapter.connectTo(device)
    }

    private inner class BluetoothConnectionObserver(

    ) : ConnectionListener {
        override fun onReceived(data: ByteArray) {
            // TODO("not implemented")
        }

        override fun onClose() {
            // TODO("not implemented")
        }

        override fun onError(exception: Throwable) {
            // TODO("not implemented")
        }

    }

}