package ru.tetraquark.bluetoothchatmpp.presentation.conversation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.tetraquark.bluetoothchatmpp.presentation.R

class ChatMessageListRvAdapter(
    private val dataSource: DataSource
) : RecyclerView.Adapter<ChatMessageListRvAdapter.ChatMessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent)
        return ChatMessageViewHolder(itemView)
    }

    override fun getItemCount(): Int = dataSource.getItemCount()

    override fun onBindViewHolder(holder: ChatMessageViewHolder, position: Int) {
        holder.bind(dataSource.getChatMessage(position))
    }

    inner class ChatMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val messageTextView = itemView.findViewById<TextView>(R.id.item_message_text)

        fun bind(chatMessage: ChatMessage?) {
            if(chatMessage != null) {
                messageTextView.text = "${chatMessage.author}: ${chatMessage.text}"
            }
        }

    }

    interface DataSource {
        fun getItemCount(): Int
        fun getChatMessage(position: Int): ChatMessage?
    }

}
