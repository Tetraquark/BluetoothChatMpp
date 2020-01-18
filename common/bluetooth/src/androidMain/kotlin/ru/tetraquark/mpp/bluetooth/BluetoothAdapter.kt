package ru.tetraquark.mpp.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.ParcelUuid
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.reflect.KProperty

actual class BluetoothAdapter(
    actual val uuid: String
) {

    private val androidBluetoothAdapter by BluetoothAdapterDelegate()

    private var context: Context? = null
    private var isDiscoverDevices = AtomicBoolean(false)

    private val deviceScannerListener = DeviceScanListener()
    private val discoveryReceiver = DiscoveryBroadcastReceiver()
    private var discoveryListener: DiscoveryListener? = null

    fun bind(lifecycle: Lifecycle, context: Context) {
        this.context = context

        val lifecycleObserver = object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroyed(source: LifecycleOwner) {
                this@BluetoothAdapter.context = null
                source.lifecycle.removeObserver(this)

                context.unregisterReceiver(discoveryReceiver)
            }

        }
        lifecycle.addObserver(lifecycleObserver)

        context.registerReceiver(discoveryReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
        context.registerReceiver(discoveryReceiver, IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED))
        context.registerReceiver(discoveryReceiver, IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED))
        context.registerReceiver(discoveryReceiver, IntentFilter(BluetoothDevice.ACTION_UUID))
    }

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

    actual fun isDiscovering(): Boolean {
        return androidBluetoothAdapter.isDiscovering
    }

    actual fun makeDeviceVisible(seconds: Int) {
        context?.let { currentContext ->
            val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, seconds)
            }
            currentContext.startActivity(discoverableIntent)
        }
    }

    actual fun startDeviceDiscovery(listener: DiscoveryListener) {
        if(ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            throw BluetoothException("Coarse location permission is not granted.")
        }

        deviceScannerListener.resetListener()

        discoveryListener = listener
        if(!isDiscoverDevices.get()) {
            isDiscoverDevices.set(true)

            val scanFilters = ScanFilter.Builder()
                //.setServiceUuid(ParcelUuid(UUID.fromString(uuid))) // TODO: possibility to scan concrete Device
                .build()
                .let { listOf(it) }

            val scanSettings = ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build()

            //androidBluetoothAdapter.bluetoothLeScanner.startScan(scanFilters, scanSettings, deviceScannerListener)
            androidBluetoothAdapter.bluetoothLeScanner.startScan(deviceScannerListener)
            listener.onDiscoveryStarted()
        }
    }

    actual fun stopDeviceDiscovery() {
        if(isDiscoverDevices.get()) {
            isDiscoverDevices.set(false)
            androidBluetoothAdapter.cancelDiscovery()
        }
    }

    actual fun getDeviceName(): String = androidBluetoothAdapter.name

    @SuppressLint("HardwareIds")
    actual fun getDeviceAddress(): String = androidBluetoothAdapter.address

    actual fun createGattConnection(bluetoothRemoteDevice: BluetoothRemoteDevice): BLEGattConnection {
        return BLEGattConnection(bluetoothRemoteDevice)
    }

    private fun onFoundBluetoothDevice(bluetoothDevice: BluetoothDevice) {
        bluetoothDevice.fetchUuidsWithSdp()
        if(bluetoothDevice.bondState != BluetoothDevice.BOND_BONDED) {
            discoveryListener?.onDeviceFound(bluetoothDevice.mapToRemoteDevice())
        }
    }

    private fun onBluetoothDeviceScanFinished() {
        discoveryListener?.onDiscoveryFinished()
        discoveryListener = null
    }

    private fun BluetoothDevice.mapToRemoteDevice(): BluetoothRemoteDevice {
        return BluetoothRemoteDevice(this)
    }

    private class BluetoothAdapterDelegate {

        private val androidBluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

        operator fun getValue(thisRef: Any?, property: KProperty<*>): BluetoothAdapter {
            return androidBluetoothAdapter
                ?: throw BluetoothException("Bluetooth is not is available.")
        }

    }

    private inner class DiscoveryBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent == null) return

            when(intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)?.let {
                        onFoundBluetoothDevice(it)
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    onBluetoothDeviceScanFinished()
                }
                BluetoothDevice.ACTION_UUID -> {
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)?.let {
                        val parcelUuid = intent.getParcelableExtra<ParcelUuid>(BluetoothDevice.EXTRA_UUID)
                    }
                }
            }
        }
    }

    private inner class DeviceScanListener : ScanCallback() {

        private val discoveredBluetoothDevicesAddresses = mutableSetOf<String>()

        fun resetListener() {
            discoveredBluetoothDevicesAddresses.clear()
        }

        override fun onScanFailed(errorCode: Int) {
            onBluetoothDeviceScanFinished()
        }

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            result.device?.let {
                if(!discoveredBluetoothDevicesAddresses.contains(it.address)) {
                    discoveredBluetoothDevicesAddresses.add(it.address)
                    onFoundBluetoothDevice(it)
                }
            }
        }
    }

}
