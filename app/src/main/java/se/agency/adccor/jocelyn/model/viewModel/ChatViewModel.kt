package se.agency.adccor.jocelyn.model.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import se.agency.adccor.jocelyn.model.ChatMessage
import se.agency.adccor.jocelyn.model.DataRepository

// interesting: https://proandroiddev.com/modern-android-development-with-kotlin-september-2017-part-2-17444fcdbe86
// https://android.jlelse.eu/android-architecture-components-now-with-100-more-mvvm-11629a630125

/**
 * Created by adamn on 11/27/2017.
 */
class ChatViewModel : ViewModel() {

    private lateinit var messages: LiveData<List<ChatMessage>> // this is a bit sucky , but is for now

    fun getMessages(): LiveData<List<ChatMessage>> = messages

    fun setup(dataRepository: DataRepository) {
        messages = dataRepository.loadMessages()
    }

}