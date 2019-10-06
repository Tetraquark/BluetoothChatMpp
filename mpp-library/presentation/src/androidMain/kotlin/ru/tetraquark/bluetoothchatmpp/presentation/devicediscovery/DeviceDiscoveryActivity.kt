package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.icerock.moko.mvvm.MvvmActivity
import dev.icerock.moko.mvvm.ViewModelFactory
import ru.tetraquark.bluetoothchatmpp.presentation.BR
import ru.tetraquark.bluetoothchatmpp.presentation.R
import ru.tetraquark.bluetoothchatmpp.presentation.databinding.ActivityDeviceDiscoveryBinding

class DeviceDiscoveryActivity : MvvmActivity<ActivityDeviceDiscoveryBinding, DeviceDiscoveryViewModel>(),
    DeviceDiscoveryViewModel.EventListener, DeviceDiscoveryInjector {

    override val layoutId: Int = R.layout.activity_device_discovery
    override val viewModelClass: Class<DeviceDiscoveryViewModel> = DeviceDiscoveryViewModel::class.java
    override val viewModelVariableId: Int = BR.deviceDiscoveryViewModel
    override fun viewModelFactory(): ViewModelProvider.Factory = viewModelFactory

    private lateinit var viewModelFactory: ViewModelFactory
    override fun injectViewModelFactory(factory: ViewModelFactory) {
        this.viewModelFactory = factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.peersRvlist.adapter = BluetoothDeviceRvAdapter(object : BluetoothDeviceRvAdapter.DataSource {
            override fun getItemCount(): Int = viewModel.discoveredPeers.value.size

            override fun getItem(position: Int): BluetoothPeer? =
                viewModel.discoveredPeers.value.get(position)
        })

        viewModel.discoveredPeers.ld().observe(this, Observer {
            binding.peersRvlist.adapter?.notifyDataSetChanged()
        })
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
