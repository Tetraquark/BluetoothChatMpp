package ru.tetraquark.bluetoothchatmpp.domain

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.livedata.readOnly
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import ru.tetraquark.bluetoothchatmpp.domain.entity.BluetoothRemoteDevice

class DiscoveryBluetoothDevicesInteractor(
    private val bluetoothDevicesRepository: BluetoothDevicesRepository
) {

    private val _discoveredDeviceList = MutableLiveData<List<BluetoothRemoteDevice>>(mutableListOf())
    val discoveredDeviceList: LiveData<List<BluetoothRemoteDevice>> = _discoveredDeviceList.map {
        it.toList()
    }

    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading.readOnly()

    suspend fun startDevicesDiscovery() {
        _isLoading.value = true
        val flow = bluetoothDevicesRepository.startDeviceDiscovery()
        try {
            flow.onEach {
                // TODO: addition of lists
                _discoveredDeviceList.value = _discoveredDeviceList.value + it
            }.collect()
        } catch (cancelException: CancellationException) {
            // Channel was closed
            println("Flow was closed?")
        } finally {
            _isLoading.value = false
        }
    }

    fun stopDevicesDiscovery() {
        bluetoothDevicesRepository.stopDeviceDiscovery()
        _isLoading.value = false
    }

    suspend fun connectToDevice(index: Int) {
        _discoveredDeviceList.value.getOrNull(index)?.let {
            bluetoothDevicesRepository.createBLEConnection(it)
        }
    }

}
