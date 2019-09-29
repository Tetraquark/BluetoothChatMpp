package ru.tetraquark.bluetoothchatmpp.presentation.conversation

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dev.icerock.moko.mvvm.MvvmActivity
import dev.icerock.moko.mvvm.ViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import ru.tetraquark.bluetoothchatmpp.presentation.BR
import ru.tetraquark.bluetoothchatmpp.presentation.R
import ru.tetraquark.bluetoothchatmpp.presentation.databinding.ActivityConversationBinding

class ConversationActivity : MvvmActivity<ActivityConversationBinding, ConversationViewModel>(),
ConversationViewModel.EventListener {

    override val layoutId: Int = R.layout.activity_conversation
    override val viewModelVariableId: Int = BR.conversationViewModel
    override val viewModelClass: Class<ConversationViewModel> = ConversationViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory {
            ConversationViewModel(eventsDispatcherOnMain())
        }
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
