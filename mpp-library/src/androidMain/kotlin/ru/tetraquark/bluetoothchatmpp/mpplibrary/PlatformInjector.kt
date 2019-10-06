package ru.tetraquark.bluetoothchatmpp.mpplibrary

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.icerock.moko.mvvm.ViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import ru.tetraquark.bluetoothchatmpp.data.RemoteDeviceRepository
import ru.tetraquark.bluetoothchatmpp.domain.DiscoveryBluetoothDevicesInteractor
import ru.tetraquark.bluetoothchatmpp.domain.BluetoothDevicesRepository
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryInteractor
import ru.tetraquark.bluetoothchatmpp.presentation.createDeviceDiscoveryViewModel
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.BluetoothPeer
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryInjector
import ru.tetraquark.mpp.bluetooth.BluetoothAdapter
import ru.tetraquark.mpp.bluetooth.BluetoothRemoteDevice
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map

actual class PlatformInjector(
    private val application: Application
) : Application.ActivityLifecycleCallbacks {

    actual fun injectPlatform() {
        application.registerActivityLifecycleCallbacks(this)
    }

    val bluetoothAdapter = BluetoothAdapter()

    val bluetoothDeviceRepository = RemoteDeviceRepository(bluetoothAdapter)

    val discoveryBluetoothDevicesInteractor = DiscoveryBluetoothDevicesInteractor(
        bluetoothDevicesRepository = object : BluetoothDevicesRepository {
            override suspend fun startDeviceDiscovery(): ReceiveChannel<ru.tetraquark.bluetoothchatmpp.domain.entity.BluetoothRemoteDevice> {
                return bluetoothDeviceRepository.startDeviceDiscovery().map {
                    ru.tetraquark.bluetoothchatmpp.domain.entity.BluetoothRemoteDevice(
                        address = it.address,
                        name = it.name,
                        type = it.type
                    )
                }
            }

            override fun stopDeviceDiscovery() {
                bluetoothDeviceRepository.stopDeviceDiscovery()
            }

        }
    )

    val deviceDiscoveryViewModelFactory = ViewModelFactory {
        createDeviceDiscoveryViewModel(
            eventsDispatcher = eventsDispatcherOnMain(),
            deviceDiscoveryInteractor = object : DeviceDiscoveryInteractor {
                override val discoveredDeviceList: LiveData<List<BluetoothPeer>> = discoveryBluetoothDevicesInteractor.discoveredDeviceList.map { list ->
                    list.map { BluetoothPeer(it.name, it.address) }
                }

                override suspend fun startDiscovery() {
                    discoveryBluetoothDevicesInteractor.startDevicesDiscovery()
                }

                override fun stopDiscovery() {
                    discoveryBluetoothDevicesInteractor.stopDevicesDiscovery()
                }
            }
        )
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        when(activity) {
            is DeviceDiscoveryInjector -> {
                activity.injectViewModelFactory(deviceDiscoveryViewModelFactory)
            }
        }

        if(activity is AppCompatActivity) {
            bluetoothAdapter.bind(activity.lifecycle, activity)
        }

    }

    override fun onActivityPaused(activity: Activity?) = Unit
    override fun onActivityResumed(activity: Activity?) = Unit
    override fun onActivityStarted(activity: Activity?) = Unit
    override fun onActivityDestroyed(activity: Activity?) = Unit
    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) = Unit
    override fun onActivityStopped(activity: Activity?) = Unit

}
