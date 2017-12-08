package se.agency.adccor.jocelyn.views

import android.app.Fragment
import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.model.ChatMessage
import se.agency.adccor.jocelyn.model.viewModel.ChatViewModel
import se.agency.adccor.jocelyn.model.viewModel.ChatViewModelFactory

class ChatFragment : Fragment(), ViewTreeObserver.OnGlobalLayoutListener {

    private val mysteriousPaddingOffset = 120 //It is not known why this is
    private var bottomPaddingOffset = 0f

    private var lastVisibleItemWhenCollapse: Int = RecyclerView.NO_POSITION

    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        val model = ViewModelProviders.of(activity as LifecycleActivity, ChatViewModelFactory(JocelynApp.dataRepository)).get(ChatViewModel::class.java)

        val adapter = ChatHistoryRecyclerViewAdapter(activity)
        model.getMessages().observe(activity as LifecycleOwner, Observer<PagedList<ChatMessage>> { pagedList ->
            adapter.setList(pagedList)
            listChatMessages.smoothScrollToPosition(adapter.itemCount)
        })

        view.listChatMessages.adapter = adapter

        view.buttonCollapse.setOnClickListener {
            mListener?.onSwipePanelCollapseExpandButton()
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(this)

        return view
    }

    override fun onGlobalLayout() {
        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
        bottomPaddingOffset = contentContainer.measuredHeight - (mysteriousPaddingOffset * resources.displayMetrics.density + 0.5f)
        onPanelSlide(0f)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    fun onPanelCollapseExpandButton(panelState: SlidingUpPanelLayout.PanelState?) {
        if (panelState.isExpanded()) {
            lastVisibleItemWhenCollapse = listChatMessages.findLastCompletelyVisibleItemPosition()
        }
    }

    fun onFragmentExpanded() {
        handlebarBorder.toStartState()
        buttonCollapse.toStartState()
        clearLastVisiblePosition()
    }

    fun onFragmentCollapsed() {
        handlebarBorder.toEndState()
        buttonCollapse.toEndState()
        if(lastVisibleItemWhenCollapse.isPosition()){
            listChatMessages.smoothScrollToPosition(lastVisibleItemWhenCollapse)
            clearLastVisiblePosition()
        }
    }

    private fun clearLastVisiblePosition() {
        lastVisibleItemWhenCollapse = RecyclerView.NO_POSITION
    }

    fun onPanelSlide(slideOffset: Float) {
        if (lastVisibleItemWhenCollapse.isNotPosition()) {
            lastVisibleItemWhenCollapse = listChatMessages.findLastCompletelyVisibleItemPosition()
        }

        listChatMessages.setPadding(
                /** A little ugly, especially using padding top at bot padding**/
                listChatMessages.paddingLeft,
                listChatMessages.paddingTop,
                listChatMessages.paddingRight,
                listChatMessages.paddingTop + (bottomPaddingOffset * (1 - slideOffset)).toInt())
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: ChatMessage)

        fun enablePanelSlide(enabled: Boolean = true)

        fun onSwipePanelCollapseExpandButton()
    }

}


private fun Int.isNotPosition(): Boolean = this == RecyclerView.NO_POSITION
private fun Int.isPosition(): Boolean = this != RecyclerView.NO_POSITION

private fun SlidingUpPanelLayout.PanelState?.isExpanded(): Boolean = this == SlidingUpPanelLayout.PanelState.EXPANDED

