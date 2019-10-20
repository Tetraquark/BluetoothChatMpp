package ru.tetraquark.bluetoothchatmpp.mpplibrary

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.icerock.moko.mvvm.ViewModelFactory
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryInjector
import ru.tetraquark.mpp.bluetooth.BluetoothAdapter

actual class PlatformInjector(
    actual val appUuid: String,
    private val application: Application
) : Application.ActivityLifecycleCallbacks {

    internal actual fun injectPlatform() {
        application.registerActivityLifecycleCallbacks(this)
    }

    val bluetoothAdapter by lazy {
        BluetoothAdapter(appUuid)
    }

    val dataLayerFactory by lazy {
        DataLayerFactoryImpl(bluetoothAdapter)
    }

    val domainFactory by lazy {
        DomainFactoryImpl(dataLayerFactory)
    }

    val presentationFactory by lazy {
        PresentationFactoryImpl(domainFactory)
    }

    val deviceDiscoveryViewModelFactory = ViewModelFactory {
        presentationFactory.createDeviceDiscoveryViewModel()
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
