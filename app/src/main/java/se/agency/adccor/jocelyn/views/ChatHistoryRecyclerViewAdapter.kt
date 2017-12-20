package se.agency.adccor.jocelyn.views

import android.app.Activity
import android.arch.paging.PagedListAdapter
import android.support.constraint.ConstraintLayout
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.chat_bubble_item.view.*
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.model.ChatMessage


class ChatHistoryRecyclerViewAdapter(private val context: Activity) : PagedListAdapter<ChatMessage, ChatHistoryRecyclerViewAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int = when (getItem(position)?.isBotMessage()) {
        true -> R.layout.chat_bubble_other_item
        else -> R.layout.chat_bubble_item
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(viewType, parent, false) as ConstraintLayout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.apply { holder.bind(this) }
    }

    inner class ViewHolder(private val mView: ConstraintLayout) : RecyclerView.ViewHolder(mView) {
        fun bind(item: ChatMessage) {
            mView.textChatContent.text = item.message
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffCallback<ChatMessage>() {

            override fun areItemsTheSame(oldMessage: ChatMessage, newMessage: ChatMessage): Boolean {
                // message properties may have changed if reloaded from the DB, but ID is fixed
                return oldMessage.uid == newMessage.uid
            }

            override fun areContentsTheSame(oldMessage: ChatMessage, newMessage: ChatMessage): Boolean {
                // NOTE: if you use equals, your object must properly override Object#equals()
                // Incorrectly returning false here will result in too many animations.
                return oldMessage == newMessage
            }
        }
    }

}

