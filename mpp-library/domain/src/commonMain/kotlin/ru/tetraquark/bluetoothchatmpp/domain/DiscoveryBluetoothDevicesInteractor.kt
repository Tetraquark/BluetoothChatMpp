package ru.tetraquark.bluetoothchatmpp.domain

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.livedata.readOnly
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tetraquark.bluetoothchatmpp.domain.entity.BLEService
import ru.tetraquark.bluetoothchatmpp.domain.entity.BluetoothRemoteDevice
import kotlin.coroutines.coroutineContext

class DiscoveryBluetoothDevicesInteractor(
    private val bluetoothDevicesRepository: BluetoothDevicesRepository
) {

    private val _discoveredDeviceList = MutableLiveData<List<BluetoothRemoteDevice>>(mutableListOf())
    val discoveredDeviceList: LiveData<List<BluetoothRemoteDevice>> = _discoveredDeviceList.map {
        it.toList()
    }

    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading.readOnly()

    private var connectedDevice: BluetoothRemoteDevice? = null

    private val _connectionState = MutableLiveData(false)
    val connectionState = _connectionState.readOnly()

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
        } finally {
            _isLoading.value = false
        }
    }

    fun stopDevicesDiscovery() {
        bluetoothDevicesRepository.stopDeviceDiscovery()
        _isLoading.value = false
    }

    suspend fun connectToDevice(index: Int) {
        _isLoading.value = true
        bluetoothDevicesRepository.stopDeviceDiscovery()

        _discoveredDeviceList.value.getOrNull(index)?.let { remoteDevice ->
            connectedDevice = remoteDevice

            bluetoothDevicesRepository.createBLEConnection(remoteDevice)?.also { flow ->
                with(CoroutineScope(coroutineContext)) {
                    launch {
                        flow.collect { _connectionState.value = it }
                    }
                }
            }
        }
        _isLoading.value = false
    }

    suspend fun disconnectFromDevice(index: Int) {
        _discoveredDeviceList.value.getOrNull(index)?.let {
            connectedDevice = null
            bluetoothDevicesRepository.closeBLEConnection(it)
        }
    }

    suspend fun discoverServices(): List<BLEService> {
        return connectedDevice?.let {
            bluetoothDevicesRepository.discoverServices(it)
        } ?: emptyList()
    }

    suspend fun readConnectionRssi(): Int {
        return connectedDevice?.let {
            bluetoothDevicesRepository.readConnectionRssi(it)
        } ?: -1
    }

}
