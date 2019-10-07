package ru.tetraquark.bluetoothchatmpp.domain

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.map
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
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

        val flow = bluetoothDevicesRepository.startDeviceDiscovery()
        try {
            flow.onEach {
                _discoveredDeviceList.value.add(it)
            }.collect()
        } catch (cancelException: CancellationException) {
            // Channel was closed
            println("{DEBUG} Flow was closed?")
        }
    }

    fun stopDevicesDiscovery() {
        bluetoothDevicesRepository.stopDeviceDiscovery()
    }

}
