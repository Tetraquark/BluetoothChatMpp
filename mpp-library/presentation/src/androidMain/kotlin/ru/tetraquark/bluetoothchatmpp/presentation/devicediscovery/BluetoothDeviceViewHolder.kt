package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.tetraquark.bluetoothchatmpp.presentation.R

class BluetoothDeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val root = itemView.findViewById<LinearLayout>(R.id.item_root)
    private val indexTextView = itemView.findViewById<TextView>(R.id.item_index)
    private val addressTextView = itemView.findViewById<TextView>(R.id.item_address)
    private val nameTextView = itemView.findViewById<TextView>(R.id.item_name)

    fun bind(
        data: BluetoothPeer?,
        clickAction: (Int) -> Unit
    ) {
        data?.let {
            indexTextView.text = layoutPosition.toString()
            addressTextView.text = it.address
            nameTextView.text = it.name
        }

        root.setOnClickListener {
            clickAction(layoutPosition)
        }
    }

}
