package se.agency.adccor.jocelyn.views

import android.app.Fragment
import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.model.ChatMessage
import se.agency.adccor.jocelyn.model.viewModel.ChatViewModel

class ChatFragment : Fragment() {
    private var mColumnCount = 1
    private var mListener: OnListFragmentInteractionListener? = null
    private var data: LiveData<List<ChatMessage>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        val recyclerView = view.list
        // Set the adapter
        if (recyclerView is RecyclerView) {
            val context = recyclerView.context
            if (mColumnCount <= 1) {
                recyclerView.layoutManager = LinearLayoutManager(context)
            } else {
                recyclerView.layoutManager = GridLayoutManager(context, mColumnCount)
            }

            val model = ViewModelProviders.of(activity as LifecycleActivity).get(ChatViewModel::class.java)
            model.setup(JocelynApp.dataRepository)
            recyclerView.adapter = ChatHistoryRecyclerViewAdapter(activity, model.getMessages(), mListener)
        }

        return view
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
    }

    fun onFragmentCollapsed() {
        handlebarBorder.toEndState()
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: ChatMessage)
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): ChatFragment {
            val fragment = ChatFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }


}
