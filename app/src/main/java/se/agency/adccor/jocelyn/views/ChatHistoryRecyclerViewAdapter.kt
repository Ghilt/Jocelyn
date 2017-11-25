package se.agency.adccor.jocelyn.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.chat_bubble_item.view.*
import se.agency.adccor.jocelyn.R
import se.agency.adccor.jocelyn.views.ChatFragment.OnListFragmentInteractionListener
import se.agency.adccor.jocelyn.views.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class ChatHistoryRecyclerViewAdapter(private val context: Context,
                                     private val mValues: List<DummyItem>,
                                     private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<ChatHistoryRecyclerViewAdapter.ViewHolder>() {

    private val state1 = ConstraintSet()
    private val state2 = ConstraintSet()

    init {
        state1.clone(context, R.layout.chat_bubble_item)
        state2.clone(context, R.layout.chat_bubble_other_item)
    }

    override fun getItemCount() = mValues.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_bubble_item, parent, false) as ConstraintLayout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mIdView.text = mValues[position].id
        holder.mContentView.text = mValues[position].content

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

        val mIdView: TextView = mView.findViewById(R.id.id)
        val mContentView: TextView = mView.findViewById(R.id.textChatContent)

        var mItem: DummyItem = DummyItem("null id", "null content", "null detail")

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
