package se.agency.adccor.jocelyn.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import se.agency.adccor.jocelyn.R

import se.agency.adccor.jocelyn.views.ChatFragment.OnListFragmentInteractionListener
import se.agency.adccor.jocelyn.views.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(private val mValues: List<DummyItem>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount() = mValues.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mIdView.text = mValues[position].id
        holder.mContentView.text = mValues[position].content

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem)
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mIdView: TextView = mView.findViewById(R.id.id)
        val mContentView: TextView = mView.findViewById(R.id.content)

        var mItem: DummyItem = DummyItem("null id","null content", "null detail")

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
