package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.tetraquark.bluetoothchatmpp.presentation.R

class BluetoothDeviceRvAdapter(
    private val dataSource: DataSource
) : RecyclerView.Adapter<BluetoothDeviceRvAdapter.BluetoothDeviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothDeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bluetooth_device, parent)
        return BluetoothDeviceViewHolder(view)
    }

    override fun getItemCount(): Int = dataSource.getItemCount()

    override fun onBindViewHolder(holder: BluetoothDeviceViewHolder, position: Int) {
        holder.bind(dataSource.getItem(position))
    }

    class BluetoothDeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val indexTextView = itemView.findViewById<TextView>(R.id.item_index)
        private val addressTextView = itemView.findViewById<TextView>(R.id.item_address)
        private val nameTextView = itemView.findViewById<TextView>(R.id.item_name)

        fun bind(data: BluetoothPeer?) {
            data?.let {
                indexTextView.text = layoutPosition.toString()
                addressTextView.text = it.address
                nameTextView.text = it.name
            }
        }

    }

    interface DataSource {

        fun getItemCount(): Int

        fun getItem(position: Int): BluetoothPeer?

    }

}