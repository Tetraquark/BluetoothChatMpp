package ru.tetraquark.bluetoothchatmpp.presentation.conversation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

class ConversationViewModel(
    override val eventsDispatcher: EventsDispatcher<EventListener>,
    private val conversationInteractor: ConversationInteractor
) : ViewModel(), EventsDispatcherOwner<ConversationViewModel.EventListener> {

    val inputMessageText: MutableLiveData<String> = MutableLiveData("")

    init {
        // TODO: connect to remote device?
    }

    fun onSendMessageButtonClick() {
        // TODO
    }

    private fun sendMessage() {
        coroutineScope.launch {
            try {
                conversationInteractor.sendNewMessage(inputMessageText.value)
            } catch (exception: Throwable) {
                eventsDispatcher.dispatchEvent { showError("Send message error.") }
            }
        }
    }

    interface EventListener {
        fun showError(message: String)
    }

}
