package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dev.icerock.moko.mvvm.MvvmActivity
import dev.icerock.moko.mvvm.ViewModelFactory
import dev.icerock.moko.widgets.core.ViewFactoryContext
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
        viewModel.eventsDispatcher.bind(this, this)

        binding.widgetContent.addView(
            viewModel.screenWidgets.buildView(
                ViewFactoryContext(
                    context = this,
                    lifecycleOwner = this
                )
            )
        )
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
