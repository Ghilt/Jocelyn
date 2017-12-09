package se.agency.adccor.jocelyn.views

import android.app.FragmentManager
import android.graphics.drawable.Animatable
import android.util.Log
import android.view.View
import se.agency.adccor.jocelyn.R

/**
 * Created by adamn on 11/24/2017.
 */

fun FragmentManager.getChatFragment() = findFragmentById(R.id.fragmentChat) as ChatFragment

fun Any.dLog(text: String = "Reached this point") {
    Log.d("${this.javaClass.simpleName}: spx", "$text")
}

fun Any.dLog(number: Int) = dLog("Reached $number")

fun View.animateBackground() {
    val plane = this.background
    if (plane is Animatable) {
        plane.start()
    }
}