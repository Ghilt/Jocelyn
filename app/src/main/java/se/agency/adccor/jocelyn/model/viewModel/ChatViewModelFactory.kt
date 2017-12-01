package se.agency.adccor.jocelyn.model.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import se.agency.adccor.jocelyn.model.DataRepository

/**
 * Created by adamn on 12/1/2017.
 */
class ChatViewModelFactory(private val repo: DataRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(repo) as T
        }
        throw Error("Not a ChatViewModel")
    }

}