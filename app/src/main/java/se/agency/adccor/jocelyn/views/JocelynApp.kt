package se.agency.adccor.jocelyn.views

import android.app.Application
import android.arch.persistence.room.Room
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import se.agency.adccor.jocelyn.model.DataRepository
import se.agency.adccor.jocelyn.model.MessageDatabase
import se.agency.adccor.jocelyn.model.network.DialogFlowWebService


/**
 * Created by adamn on 11/26/2017.
 */

class JocelynApp : Application() {

    companion object {
        lateinit var dataRepository: DataRepository //TODO Later learn dagger 2
        lateinit var service: DialogFlowWebService
    }

    override fun onCreate() {
        super.onCreate()
        // fallbackToDestructiveMigration TODO add migration policy
        val roomDB = Room.databaseBuilder(this, MessageDatabase::class.java, "message_db").fallbackToDestructiveMigration().build()

        setupRetrofit()

        JocelynApp.dataRepository = DataRepository(service, roomDB.chatMessageDao())
    }

    private fun setupRetrofit() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        val gson = GsonBuilder().setLenient().create()
        val builder = Retrofit.Builder()
                .baseUrl("https://api.dialogflow.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())

        val retrofit = builder.build()

        JocelynApp.service = retrofit.create(DialogFlowWebService::class.java)
    }
}