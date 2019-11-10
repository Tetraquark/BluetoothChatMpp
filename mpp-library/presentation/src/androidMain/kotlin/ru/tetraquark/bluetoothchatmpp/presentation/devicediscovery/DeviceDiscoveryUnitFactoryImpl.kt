package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import dev.icerock.moko.units.UnitItem
import ru.tetraquark.bluetoothchatmpp.presentation.R

class DeviceDiscoveryUnitFactoryImpl : DeviceDiscoveryUnitFactory {

    override fun getBluetoothPeerUnit(
        bluetoothPeer: BluetoothPeer,
        onClickAction: (Int) -> Unit
    ): UnitItem {
        return object : UnitItem {
            override val viewType: Int = 0
            override val itemId: Long = bluetoothPeer.hashCode().toLong()

            override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder) {
                (viewHolder as? BluetoothDeviceViewHolder)?.bind(bluetoothPeer, onClickAction)
            }

            override fun createViewHolder(
                parent: ViewGroup,
                lifecycleOwner: LifecycleOwner
            ): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bluetooth_device, parent, false)
                return BluetoothDeviceViewHolder(view)
            }
        }
    }

}
