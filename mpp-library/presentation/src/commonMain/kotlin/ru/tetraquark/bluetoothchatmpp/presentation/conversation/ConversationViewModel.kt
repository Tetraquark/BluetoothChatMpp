package ru.tetraquark.bluetoothchatmpp.presentation.conversation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class ConversationViewModel(
    override val eventsDispatcher: EventsDispatcher<EventListener>
) : ViewModel(), EventsDispatcherOwner<ConversationViewModel.EventListener> {

    init {
        // TODO: connect to remote device
    }

    fun onSendMessageButtonClick() {
        // TODO
    }

    interface EventListener {
        fun showError(message: String)
    }

}
