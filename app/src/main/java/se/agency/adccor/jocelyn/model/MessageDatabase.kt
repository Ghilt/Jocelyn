package se.agency.adccor.jocelyn.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by adamn on 11/26/2017.
 */

@Database(entities = arrayOf(ChatMessage::class), version = 2)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun chatMessageDao(): ChatMessageDao
}

// TODO https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread write a teste like described in the answer