package ru.tetraquark.mpp.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter

actual class BluetoothAdapter {

    private val androidBluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    actual fun isAvailable(): Boolean {
        return androidBluetoothAdapter != null
    }

    actual fun isEnabled(): Boolean {
        return getAdapterOrThrow().isEnabled
    }

    actual fun getDeviceName(): String = getAdapterOrThrow().name

    @SuppressLint("HardwareIds")
    actual fun getDeviceAddress(): String = getAdapterOrThrow().address

    private fun getAdapterOrThrow(): BluetoothAdapter {
        return androidBluetoothAdapter ?: throw BluetoothException("Bluetooth is not is available.")
    }

}
