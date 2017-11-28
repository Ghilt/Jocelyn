package se.agency.adccor.jocelyn.views

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.chat_bubble_item.view.*
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.model.ChatMessage
import se.agency.adccor.jocelyn.views.ChatFragment.OnListFragmentInteractionListener

class ChatHistoryRecyclerViewAdapter(private val context: Context,
                                     private val mValues: LiveData<List<ChatMessage>>,
                                     private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<ChatHistoryRecyclerViewAdapter.ViewHolder>() {

    private val state1 = ConstraintSet()
    private val state2 = ConstraintSet()

    init {
        state1.clone(context, R.layout.chat_bubble_item)
        state2.clone(context, R.layout.chat_bubble_other_item)

        mValues.observe(context as LifecycleOwner, Observer<List<ChatMessage>> { t -> // well this can't be right can it? DiffUtil maybe but still https://stackoverflow.com/questions/44489235/update-recyclerview-with-android-livedata
            notifyDataSetChanged()
        })
    }

    override fun getItemCount() = mValues.value?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_bubble_item, parent, false) as ConstraintLayout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues.value!![position]
        holder.mContentView.text = mValues.value!![position].message

        if (position % 2 == 0) {
            state1.applyTo(holder.mView)
            holder.mView.textChatContent.background = context.getDrawable(R.drawable.chat_bubble)
        } else {
            state2.applyTo(holder.mView)
            holder.mView.textChatContent.background = context.getDrawable(R.drawable.chat_bubble_other)
        }

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem)
        }


    }

    inner class ViewHolder(val mView: ConstraintLayout) : RecyclerView.ViewHolder(mView) {

        val mContentView: TextView = mView.findViewById(R.id.textChatContent)

        lateinit var mItem: ChatMessage // Is this a late init scenario

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
