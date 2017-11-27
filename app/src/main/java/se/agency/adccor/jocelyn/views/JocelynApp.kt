package se.agency.adccor.jocelyn.views

import android.app.Application
import android.arch.persistence.room.Room
import se.agency.adccor.jocelyn.model.DataRepository
import se.agency.adccor.jocelyn.model.MessageDatabase

/**
 * Created by adamn on 11/26/2017.
 */

class JocelynApp : Application() {

    companion object {
        lateinit var dataRepository: DataRepository
    }

    override fun onCreate() {
        super.onCreate()
        JocelynApp.dataRepository =  DataRepository(Room.databaseBuilder(this, MessageDatabase::class.java, "message_db").build())
    }
}