package se.agency.adccor.jocelyn.views.listeners

import android.util.Log
import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import se.agency.adccor.jocelyn.views.ChatFragment


/**
 * Created by adamn on 11/24/2017.
 */

class ChatFragmentSlideListener(private val chatFragment: ChatFragment) : SlidingUpPanelLayout.PanelSlideListener {

    private var previousStaticState = PanelState.COLLAPSED

    override fun onPanelSlide(panel: View?, slideOffset: Float) {
        chatFragment.onPanelSlide(slideOffset)
    }

    override fun onPanelStateChanged(panel: View?, previousState: PanelState?, newState: PanelState?) {
        if (newState == PanelState.EXPANDED && newState != previousStaticState) {
            chatFragment.onFragmentExpanded()
        } else if (newState == PanelState.COLLAPSED && newState != previousStaticState) {
            chatFragment.onFragmentCollapsed()
        }

        if (newState.isStatic()) {
            previousStaticState = newState ?: previousStaticState
        }
    }
}

fun PanelState?.isStatic() = this == PanelState.EXPANDED || this == PanelState.COLLAPSED

fun SlidingUpPanelLayout.switchPanelState() {
    panelState = if (this.panelState == PanelState.COLLAPSED) PanelState.EXPANDED else PanelState.COLLAPSED
}