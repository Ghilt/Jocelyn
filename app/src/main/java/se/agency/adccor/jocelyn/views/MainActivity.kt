package se.agency.adccor.jocelyn.views

import android.app.Activity
import android.os.Bundle
import com.sothree.slidinguppanel.SlidingUpPanelLayout
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
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem) {

    }
}
