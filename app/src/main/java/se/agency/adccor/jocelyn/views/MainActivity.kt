package se.agency.adccor.jocelyn.views

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.util.Log
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.chat_input_layout.*
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.model.ChatMessage
import se.agency.adccor.jocelyn.views.dummy.DummyContent
import se.agency.adccor.jocelyn.views.listeners.ChatFragmentSlideListener
import android.os.AsyncTask
import android.arch.lifecycle.ViewModelProviders
import se.agency.adccor.jocelyn.model.viewModel.ChatViewModel


/**
 * Created by adamn on 11/20/2017.
 */

/** LifecycleActivity deprecated in favor of appcompat, but hopefully They will bring the lifecycle stuff into regular activity soon**/
class MainActivity : LifecycleActivity(), ChatFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val slidingLayout: SlidingUpPanelLayout = findViewById(R.id.sliding_layout)
        val chatFragment = fragmentManager.getChatFragment()
        slidingLayout.addPanelSlideListener(ChatFragmentSlideListener(this, chatFragment))

        val dbdao = JocelynApp.dataRepository.chatMessageDao()

        class Task : AsyncTask<Void, Void, LiveData<List<ChatMessage>>>() {
            override fun doInBackground(vararg params: Void): LiveData<List<ChatMessage>>{
                dbdao.insert(ChatMessage(0))
                return dbdao.getAllMessages()
            }

            override fun onPostExecute(messages: LiveData<List<ChatMessage>>?) {
                Log.d("spx" , " cha " + messages?.value?.size)
            }
        }

        val model = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        model.setup(JocelynApp.dataRepository)
        model.getMessages().observe(this,  object : Observer<List<ChatMessage>> { //converting this to lamba is imo maybe an example of when kotlin can be not verbose enough

            override fun onChanged(t: List<ChatMessage>?) {
                buttonSend.text = "${t?.size}"
            }
        })

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
