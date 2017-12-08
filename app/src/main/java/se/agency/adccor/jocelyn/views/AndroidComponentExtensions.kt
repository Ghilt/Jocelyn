package se.agency.adccor.jocelyn.views

import android.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import se.agency.adccor.jocelyn.R

/**
 * Created by adamn on 11/24/2017.
 */

fun FragmentManager.getChatFragment() = findFragmentById(R.id.fragmentChat) as ChatFragment

fun RecyclerView.findLastCompletelyVisibleItemPosition(): Int {
    val manager = this.layoutManager
    if (manager is LinearLayoutManager) {
        return manager.findLastCompletelyVisibleItemPosition()
    } else {
        throw Error("Developer exception: Used extension method on RecyclerView not using a LinearLayoutManager ")
    }
}
