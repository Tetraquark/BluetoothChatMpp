package ru.tetraquark.mpp.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.*
import kotlin.reflect.KProperty

actual class BluetoothAdapter {

    private val androidBluetoothAdapter by BluetoothAdapterDelegate()

    private var context: Context? = null
    private var isDiscoverDevices = false

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

    actual fun makeDeviceVisible(seconds: Int) {
        context?.let { currentContext ->
            val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, seconds)
            }
            currentContext.startActivity(discoverableIntent)
        }
    }

    actual fun startDeviceDiscovery(listener: DiscoveryListener) {
        discoveryListener = listener
        if(!isDiscoverDevices) {
            isDiscoverDevices = true
            androidBluetoothAdapter.startDiscovery()
        }
    }

    actual fun stopDeviceDiscovery() {
        if(isDiscoverDevices) {
            isDiscoverDevices = false
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

    private inner class DiscoveryBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent == null) return

            when(intent.action) {
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    discoveryListener?.onDiscoveryStarted()
                }
                BluetoothDevice.ACTION_FOUND -> {
                    val bluetoothDevice = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    if(bluetoothDevice != null && bluetoothDevice.bondState != BluetoothDevice.BOND_BONDED) {
                        discoveryListener?.onDeviceFound(
                            BluetoothRemoteDevice(
                                address = bluetoothDevice.address.orEmpty(),
                                name = bluetoothDevice.name.orEmpty(),
                                type = bluetoothDevice.type
                        ))
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    discoveryListener?.onDiscoveryFinished()
                    discoveryListener = null
                }
            }
        }
    }

}
