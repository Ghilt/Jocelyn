package se.agency.adccor.jocelyn.views

import android.app.Activity
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.util.Log
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.chat_input_layout.*
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.views.dummy.DummyContent
import se.agency.adccor.jocelyn.views.listeners.ChatFragmentSlideListener

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

        buttonSend.setOnClickListener {
            Log.d("spx ", "   af af ")
            val plane = it.background
            if(plane is Animatable){
                plane.start()
            }
        }
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem) {

    }
}
