package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.widgets.*
import dev.icerock.moko.widgets.core.WidgetScope
import dev.icerock.moko.widgets.style.view.Alignment
import dev.icerock.moko.widgets.style.view.WidgetSize
import kotlinx.coroutines.launch

class DeviceDiscoveryViewModel(
    override val eventsDispatcher: EventsDispatcher<EventListener>,
    private val deviceDiscoveryInteractor: DeviceDiscoveryInteractor,
    private val unitFactory: DeviceDiscoveryUnitFactory,
    private val widgetScope: WidgetScope = WidgetScope()
) : ViewModel(), EventsDispatcherOwner<DeviceDiscoveryViewModel.EventListener> {

    private val discoveredPeers: LiveData<List<BluetoothPeer>> = deviceDiscoveryInteractor.discoveredDeviceList

    val isLoading = deviceDiscoveryInteractor.isLoading

    val screenWidgets = widgetScope.linear(
        childs = listOf(
            widgetScope.container(
                childs = mapOf(
                    widgetScope.button(
                        text = widgetScope.const("Discover")
                    ) {
                        println("OnButton click")
                    } to Alignment.CENTER
                ),
                styled = {
                    ContainerWidget.Style(
                        size = WidgetSize.Const()
                    )
                }
            ),

            widgetScope.list(
                id = WidgetId.PeerListId,
                items = discoveredPeers.map {
                    it.map {
                        unitFactory.getBluetoothPeerUnit(it) {
                            println("On item click: $it")
                        }
                    }
                }
            )
        )
    )

    init {
        viewModelScope.launch {
            deviceDiscoveryInteractor.startDiscovery()
        }
    }

    object WidgetId {
        object PeerListId : ListWidget.Id
    }

    interface EventListener {
        fun showError(message: String)
    }

}
