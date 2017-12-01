package se.agency.adccor.jocelyn.model

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListProvider
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * Created by adamn on 11/26/2017.
 */
@Dao
interface ChatMessageDao {

    @Query("SELECT * FROM message")
    fun getAllMessages(): LiveData<List<ChatMessage>>

    @Query("SELECT * FROM message")
    fun getMessagesPaged(): LivePagedListProvider<Int, ChatMessage>

    @Insert
    fun insert(message: ChatMessage)
}