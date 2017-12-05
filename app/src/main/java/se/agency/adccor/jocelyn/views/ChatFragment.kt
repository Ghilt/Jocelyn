package se.agency.adccor.jocelyn.views

import android.app.Fragment
import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.model.ChatMessage
import se.agency.adccor.jocelyn.model.viewModel.ChatViewModel
import se.agency.adccor.jocelyn.model.viewModel.ChatViewModelFactory

class ChatFragment : Fragment(), ViewTreeObserver.OnGlobalLayoutListener {

    val mysteriousPaddingOffset = 120 //It is not known why this is

    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        val model = ViewModelProviders.of(activity as LifecycleActivity, ChatViewModelFactory(JocelynApp.dataRepository)).get(ChatViewModel::class.java)

        val adapter = ChatHistoryRecyclerViewAdapter(activity)
        model.getMessages().observe(activity as LifecycleOwner, Observer<PagedList<ChatMessage>> { pagedList -> adapter.setList(pagedList) })
        view.listChatMessages.adapter = adapter

        view.buttonCollapse.setOnClickListener {
            mListener?.swipePanel()
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(this)

        return view
    }

    override fun onGlobalLayout() {
        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
        val height = contentContainer.measuredHeight - (mysteriousPaddingOffset * resources.displayMetrics.density + 0.5f)
        (view.listChatMessages.adapter as ChatHistoryRecyclerViewAdapter).setBottomPaddingOffset(height)
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

    fun onFragmentExpanded() {
        handlebarBorder.toStartState()
        buttonCollapse.toStartState()
        mListener?.enablePanelSlide(false)
    }

    fun onFragmentCollapsed() {
        handlebarBorder.toEndState()
        buttonCollapse.toEndState()
        listChatMessages.smoothScrollToPosition(listChatMessages.adapter.itemCount - 1)
        mListener?.enablePanelSlide()
    }

    fun onPanelSlide(slideOffset: Float) {// yay hacky
        (listChatMessages.adapter as ChatHistoryRecyclerViewAdapter).onPanelSlide(slideOffset)
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: ChatMessage)

        fun enablePanelSlide(enabled: Boolean = true)

        fun swipePanel()
    }

//    companion object {
//        fun newInstance(columnCount: Int): ChatFragment {
//            val fragment = ChatFragment()
//            val args = Bundle()
//            fragment.arguments = args
//            return fragment
//        }
//    }


}
