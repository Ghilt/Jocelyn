package se.agency.adccor.jocelyn.views

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.chat_input_layout.*
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.model.ChatMessage
import se.agency.adccor.jocelyn.model.network.DialogFlowMessage
import se.agency.adccor.jocelyn.model.network.Resource
import se.agency.adccor.jocelyn.model.viewModel.ChatViewModel
import se.agency.adccor.jocelyn.model.viewModel.ChatViewModelFactory
import se.agency.adccor.jocelyn.views.listeners.ChatFragmentSlideListener
import se.agency.adccor.jocelyn.views.listeners.switchPanelState


/**
 * Created by adamn on 11/20/2017.
 */

/** LifecycleActivity deprecated in favor of appcompat, but hopefully They will bring the lifecycle stuff into regular activity soon**/
class MainActivity : LifecycleActivity(), ChatFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chatFragment = fragmentManager.getChatFragment()
        slidingLayout.addPanelSlideListener(ChatFragmentSlideListener(chatFragment))

        val model = ViewModelProviders.of(this, ChatViewModelFactory(JocelynApp.dataRepository)).get(ChatViewModel::class.java)

        model.getMessages().observe(this, Observer<PagedList<ChatMessage>> { t ->
            //            buttonSend.text = "${t?.size}"
        })

        buttonSend.setOnClickListener(::onSendButtonClick)

    }

    private fun onSendButtonClick(v: View) {
        val message: String = textChatInput.text.toString()
        textChatInput.setText("", TextView.BufferType.EDITABLE)

        JocelynApp.dataRepository.insertNewMessage(ChatMessage(0, message))

//        val data = JocelynApp.dataRepository.getChatResponse(message)
//        data.observe(this, Observer<DialogFlowMessage> { messageResponse ->
//            JocelynApp.dataRepository.insertNewMessage(ChatMessage(0, messageResponse?.name ?: "response was null"))
//        })

        val data = JocelynApp.dataRepository.getChatResponse2(message)
        data.observe(this, Observer<Resource<DialogFlowMessage?>> { messageResponse ->
            if (messageResponse != null){
                dLog("print status ${messageResponse.status}")
            }
        })

        v.animateBackground()
    }

    override fun onListFragmentInteraction(item: ChatMessage) {

    }

    override fun enablePanelSlide(enabled: Boolean) {
        slidingLayout.isTouchEnabled = enabled
    }

    override fun onSwipePanelCollapseExpandButton() {
        slidingLayout.switchPanelState()
    }
}

