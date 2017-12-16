package se.agency.adccor.jocelyn.views

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.model.DataRepository
import se.agency.adccor.jocelyn.model.MessageDatabase
import se.agency.adccor.jocelyn.model.network.DialogFlowWebService
import se.agency.adccor.jocelyn.model.network.HeaderQueryHttpInterceptor


/**
 * Created by adamn on 11/26/2017.
 */

class JocelynApp : Application() {

    companion object {
        lateinit var appContext: Context
        lateinit var dataRepository: DataRepository //TODO Later learn dagger 2
        lateinit var service: DialogFlowWebService
    }

    init {
        appContext = this
    }

    override fun onCreate() {
        super.onCreate()
        // fallbackToDestructiveMigration TODO add migration policy
        val roomDB = Room.databaseBuilder(this, MessageDatabase::class.java, getString(R.string.room_message_database_name)).fallbackToDestructiveMigration().build()

        setupRetrofit()

        JocelynApp.dataRepository = DataRepository(service, roomDB.chatMessageDao())
    }

    private fun setupRetrofit() {
        val sharedPref = getCommonPrefs()
        val sessionId = sharedPref.getUUID()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val globalHeaderQueryParams = HeaderQueryHttpInterceptor()
        globalHeaderQueryParams.addHeader(getString(R.string.retrofit_header_auth), "Bearer cdfb636b6b7341c99c448c64d090577e") // todo secretify
        globalHeaderQueryParams.addQuaryParam(getString(R.string.retrofit_query_param_session_id), sessionId)
        globalHeaderQueryParams.addQuaryParam(getString(R.string.retrofit_query_param_language), getString(R.string.retrofit_query_param_language_value))
        globalHeaderQueryParams.addQuaryParam(getString(R.string.retrofit_query_param_version), getString(R.string.retrofit_query_param_version_value))

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(globalHeaderQueryParams)

        val gson = GsonBuilder().setLenient().create()
        val builder = Retrofit.Builder()
                .baseUrl(getString(R.string.retrofit_base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())

        val retrofit = builder.build()

        JocelynApp.service = retrofit.create(DialogFlowWebService::class.java)
    }
}
