package se.agency.adccor.jocelyn.views

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.Animatable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.chat_input_layout.*
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.model.ChatMessage
import se.agency.adccor.jocelyn.model.viewModel.ChatViewModel
import se.agency.adccor.jocelyn.views.listeners.ChatFragmentSlideListener


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


        val model = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        model.setup(JocelynApp.dataRepository) // this is called twice, obviously really really dumb
        model.getMessages().observe(this, Observer<List<ChatMessage>> { t -> //converting this to lamba is imo maybe an example of when kotlin can be not verbose enough

            buttonSend.text = "${t?.size}"
        })

        buttonSend.setOnClickListener {
            val message: String = textChatInput.text.toString()
            textChatInput.setText("", TextView.BufferType.EDITABLE)

            JocelynApp.dataRepository.insertNewMessage(ChatMessage(0, message))
            animateView(it)
        }

    }

    private fun animateView(it: View) {
        val plane = it.background
        if (plane is Animatable) {
            plane.start()
        }
    }

    override fun onListFragmentInteraction(item: ChatMessage) {

    }
}
