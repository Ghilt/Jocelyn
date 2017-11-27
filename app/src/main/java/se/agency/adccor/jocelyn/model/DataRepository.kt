package se.agency.adccor.jocelyn.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

/**
 * Created by adamn on 11/27/2017.
 */
class DataRepository(private val dataBase: MessageDatabase) {

    fun loadMessages() : LiveData<List<ChatMessage>> {
//        val m = ArrayList<ChatMessage>()
//        m.add(ChatMessage(0))
//        m.add(ChatMessage(1))
//        m.add(ChatMessage(2))
//        m.add(ChatMessage(3))
//        m.add(ChatMessage(4))
//        val live = MutableLiveData<List<ChatMessage>>()
//        live.value = m
        return dataBase.chatMessageDao().getAllMessages()
    }

    fun chatMessageDao() = dataBase.chatMessageDao() // TODO what exaclty is the repositoies responsibility?, does it give out DAO's? probably not

}