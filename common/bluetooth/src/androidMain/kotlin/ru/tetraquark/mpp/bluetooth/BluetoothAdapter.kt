package ru.tetraquark.mpp.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import kotlin.reflect.KProperty

actual class BluetoothAdapter {

    private val androidBluetoothAdapter by BluetoothAdapterDelegate()

    private var isDiscoverDevices = false

    actual fun isAvailable(): Boolean {
        return try {
            androidBluetoothAdapter.isEnabled
            true
        } catch (exception: BluetoothException) {
            false
        }
    }

    actual fun isEnabled(): Boolean {
        return androidBluetoothAdapter.isEnabled
    }

    actual fun startDeviceDiscovery() {
        if(!isDiscoverDevices) {
            androidBluetoothAdapter.startDiscovery()
        }
    }

    actual fun stopDeviceDiscovery() {
        if(isDiscoverDevices) {
            androidBluetoothAdapter.cancelDiscovery()
        }
    }

    actual fun getDeviceName(): String = androidBluetoothAdapter.name

    @SuppressLint("HardwareIds")
    actual fun getDeviceAddress(): String = androidBluetoothAdapter.address


    private class BluetoothAdapterDelegate {

        private val androidBluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

        operator fun getValue(thisRef: Any?, property: KProperty<*>): BluetoothAdapter {
            return androidBluetoothAdapter
                ?: throw BluetoothException("Bluetooth is not is available.")
        }

    }

}
