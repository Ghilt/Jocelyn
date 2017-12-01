package se.agency.adccor.jocelyn.views

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.chat_bubble_item.view.*
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.model.ChatMessage

class ChatHistoryRecyclerViewAdapter(private val context: Context) : PagedListAdapter<ChatMessage, ChatHistoryRecyclerViewAdapter.ViewHolder>(DIFF_CALLBACK) {

    private val state1 = ConstraintSet()
    private val state2 = ConstraintSet()

    init {
        state1.clone(context, R.layout.chat_bubble_item)
        state2.clone(context, R.layout.chat_bubble_other_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_bubble_item, parent, false) as ConstraintLayout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = getItem(position)

        if (holder.mItem == null) {
            Log.d("spx", "null item at $position")
            return
        }

        holder.mContentView.text = holder.mItem?.message

        if (position % 2 == 0) {
            state1.applyTo(holder.mView)
            holder.mView.textChatContent.background = context.getDrawable(R.drawable.chat_bubble)
        } else {
            state2.applyTo(holder.mView)
            holder.mView.textChatContent.background = context.getDrawable(R.drawable.chat_bubble_other)
        }

    }

    inner class ViewHolder(val mView: ConstraintLayout) : RecyclerView.ViewHolder(mView) {

        val mContentView: TextView = mView.findViewById(R.id.textChatContent)

        var mItem: ChatMessage? = null // Is this a late init scenario

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffCallback<ChatMessage>() {

            override fun areItemsTheSame(oldMessage: ChatMessage, newMessage: ChatMessage): Boolean {
                // Message properties may have changed if reloaded from the DB, but ID is fixed
                return oldMessage.uid == newMessage.uid
            }

            override fun areContentsTheSame(oldMessage: ChatMessage, newMessage: ChatMessage): Boolean {
                // NOTE: if you use equals, your object must properly override Object#equals()
                // Incorrectly returning false here will result in too many animations.
                return oldMessage.equals(newMessage)// TODO
            }
        }
    }
}
