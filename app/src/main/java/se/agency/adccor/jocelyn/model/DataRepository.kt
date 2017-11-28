package se.agency.adccor.jocelyn.model

import android.arch.lifecycle.LiveData
import android.os.AsyncTask

/**
 * Created by adamn on 11/27/2017.
 */
class DataRepository(private val dao: ChatMessageDao) {

    fun loadMessages() : LiveData<List<ChatMessage>> {
        return dao.getAllMessages()
    }

    fun insertNewMessage(chatMessage: ChatMessage) {
        AsyncTask.execute{
            dao.insert(chatMessage)
        }
    }


}