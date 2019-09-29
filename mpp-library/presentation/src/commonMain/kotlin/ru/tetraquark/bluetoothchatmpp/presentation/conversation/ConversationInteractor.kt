package ru.tetraquark.bluetoothchatmpp.presentation.conversation

interface ConversationInteractor {

    suspend fun sendNewMessage(text: String)

}
