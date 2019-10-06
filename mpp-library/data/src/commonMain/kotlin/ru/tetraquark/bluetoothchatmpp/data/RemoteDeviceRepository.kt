package ru.tetraquark.bluetoothchatmpp.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import ru.tetraquark.mpp.bluetooth.BluetoothAdapter
import ru.tetraquark.mpp.bluetooth.BluetoothRemoteDevice
import ru.tetraquark.mpp.bluetooth.DiscoveryListener
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine

class RemoteDeviceRepository(
    private val bluetoothAdapter: BluetoothAdapter
) : CoroutineScope {

    private val repositoryJob = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + repositoryJob

    fun startDeviceDiscovery(): ReceiveChannel<BluetoothRemoteDevice> {
        val channel = BroadcastChannel<BluetoothRemoteDevice>(Channel.BUFFERED)

        bluetoothAdapter.startDeviceDiscovery(object : DiscoveryListener {
            override fun onDiscoveryStarted() = Unit

            override fun onDeviceFound(bluetoothRemoteDevice: BluetoothRemoteDevice) {
                launch {
                    channel.send(bluetoothRemoteDevice)
                }
            }

            override fun onDiscoveryFinished() {
                launch {
                    channel.cancel()
                }
            }
        })

        return channel.openSubscription()
    }

    fun stopDeviceDiscovery() {
        bluetoothAdapter.stopDeviceDiscovery()
    }

}