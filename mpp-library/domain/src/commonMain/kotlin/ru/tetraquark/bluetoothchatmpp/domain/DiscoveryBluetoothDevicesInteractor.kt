package ru.tetraquark.bluetoothchatmpp.domain

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.map
import kotlinx.coroutines.CancellationException
import ru.tetraquark.bluetoothchatmpp.domain.entity.BluetoothRemoteDevice

class DiscoveryBluetoothDevicesInteractor(
    private val bluetoothDevicesRepository: BluetoothDevicesRepository
) {

    private val _discoveredDeviceList = MutableLiveData<MutableList<BluetoothRemoteDevice>>(mutableListOf())
    val discoveredDeviceList: LiveData<List<BluetoothRemoteDevice>> = _discoveredDeviceList.map {
        it.toList()
    }

    suspend fun startDevicesDiscovery() {
        _discoveredDeviceList.value.clear()

        val channel = bluetoothDevicesRepository.startDeviceDiscovery()
        try {
            for (bluetoothDevice in channel) {
                _discoveredDeviceList.value.add(bluetoothDevice)
            }
        } catch (cancelException: CancellationException) {
            // Channel was closed
            println("{DEBUG} Channel was closed")
        }
    }

    fun stopDevicesDiscovery() {
        bluetoothDevicesRepository.stopDeviceDiscovery()
    }

}
