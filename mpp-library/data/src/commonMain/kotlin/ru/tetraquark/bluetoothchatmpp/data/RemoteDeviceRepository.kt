package ru.tetraquark.bluetoothchatmpp.data

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ru.tetraquark.mpp.bluetooth.BluetoothAdapter
import ru.tetraquark.mpp.bluetooth.BluetoothRemoteDevice
import ru.tetraquark.mpp.bluetooth.DiscoveryListener
import kotlin.coroutines.CoroutineContext

class RemoteDeviceRepository(
    private val bluetoothAdapter: BluetoothAdapter
) : CoroutineScope {

    private val repositoryJob = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + repositoryJob

    fun startDeviceDiscovery(): Flow<BluetoothRemoteDevice> {
        return callbackFlow {
            bluetoothAdapter.startDeviceDiscovery(object : DiscoveryListener {
                override fun onDiscoveryStarted() = Unit

                override fun onDeviceFound(bluetoothRemoteDevice: BluetoothRemoteDevice) {
                    offer(bluetoothRemoteDevice)
                }

                override fun onDiscoveryFinished() {
                    close()
                }
            })
            awaitClose { cancel() }
        }
    }

    fun stopDeviceDiscovery() {
        bluetoothAdapter.stopDeviceDiscovery()
    }

}