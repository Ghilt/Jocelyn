package se.agency.adccor.jocelyn.model.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import se.agency.adccor.jocelyn.model.ChatMessage
import se.agency.adccor.jocelyn.model.DataRepository


// interesting: https://proandroiddev.com/modern-android-development-with-kotlin-september-2017-part-2-17444fcdbe86
// https://android.jlelse.eu/android-architecture-components-now-with-100-more-mvvm-11629a630125

/**
 * Created by adamn on 11/27/2017.
 */
class ChatViewModel(dataRepository: DataRepository) : ViewModel() {

    private var messages: LiveData<PagedList<ChatMessage>> = dataRepository.messagesPaged().create(
            0,
            PagedList.Config.Builder()
                    .setPageSize(50)
                    .setPrefetchDistance(50)
                    .build())

    fun getMessages(): LiveData<PagedList<ChatMessage>> = messages


}