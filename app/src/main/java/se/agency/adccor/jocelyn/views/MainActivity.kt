package se.agency.adccor.jocelyn.views

import android.app.Activity
import android.arch.persistence.room.Room
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.util.Log
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.chat_input_layout.*
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.model.ChatMessage
import se.agency.adccor.jocelyn.model.MessageDatabase
import se.agency.adccor.jocelyn.views.dummy.DummyContent
import se.agency.adccor.jocelyn.views.listeners.ChatFragmentSlideListener
import android.widget.Toast
import android.os.AsyncTask



/**
 * Created by adamn on 11/20/2017.
 */

class MainActivity : Activity(), ChatFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val slidingLayout: SlidingUpPanelLayout = findViewById(R.id.sliding_layout)
        val chatFragment = fragmentManager.getChatFragment()
        slidingLayout.addPanelSlideListener(ChatFragmentSlideListener(this, chatFragment))

        val dbdao = JocelynApp.database.chatMessageDao()

        class Task : AsyncTask<Void, Void, List<ChatMessage>>() {
            override fun doInBackground(vararg params: Void): List<ChatMessage> {
                dbdao.insert(ChatMessage(0))
                return dbdao.getAllMessages()
            }

            override fun onPostExecute(messages: List<ChatMessage>?) {
                Log.d("spx" , " cha " + messages?.size)
            }
        }

        buttonSend.setOnClickListener {
            Log.d("spx ", "   af af ")
            val plane = it.background
            if(plane is Animatable){
                plane.start()
            }

            Task().execute()
        }
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem) {

    }
}
