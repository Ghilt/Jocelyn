package se.agency.adccor.jocelyn.views

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.views.dummy.DummyContent

/**
 * Created by adamn on 11/20/2017.
 */

class MainActivity : Activity(), ChatFragment.OnListFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val slidingLayout: SlidingUpPanelLayout = findViewById(R.id.sliding_layout)
        slidingLayout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                Log.d("spx", "test On panel slide")
            }

            override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
                Log.d("spx", "test On panel slide state changed $previousState $newState")

                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED){
//                    slidingLayout.isTouchEnabled = false
                }
            }

        })
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
