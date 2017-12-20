package se.agency.adccor.jocelyn.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

/**
 * Created by adamn on 11/26/2017.
 */

@Database(entities = [(ChatMessage::class)], version = 8)
@TypeConverters(DialogFlowTypeConverters::class)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun chatMessageDao(): ChatMessageDao
}

// TODO https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread write a test like described in the answer