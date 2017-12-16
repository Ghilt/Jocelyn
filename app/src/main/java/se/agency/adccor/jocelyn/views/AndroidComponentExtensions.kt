package se.agency.adccor.jocelyn.views

import android.app.FragmentManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Animatable
import android.util.Log
import android.view.View
import se.agency.adccor.jocelyn.R
import java.util.*

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

fun Context.getCommonPrefs() : SharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)

fun SharedPreferences.getUUID(): String {
    val key = JocelynApp.appContext.getString(R.string.preference_key_uuid)
    return if (this.contains(key)) {
         this.getString(key,"")
    } else {
        val uniqueID = UUID.randomUUID().toString()
        this.edit().putString(key, uniqueID).apply()
        uniqueID
    }
}
