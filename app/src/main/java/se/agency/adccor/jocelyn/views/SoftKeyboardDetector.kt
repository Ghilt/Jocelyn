package se.agency.adccor.jocelyn.views

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver


/**
 * Created by adamn on 12/21/2017 with a big sigh
 */
class SoftKeyboardDetector(private val context: Context,
                           private val view: View,
                           private val listener: OnKeyboardListener ) : ViewTreeObserver.OnGlobalLayoutListener {
    companion object {
        val KEYBOARD_HEIGHT_APPROXIMATELY = 200f// if more than 200 dp, it's probably a keyboard...
    }

    override fun onGlobalLayout() {
        val heightDiff = view.rootView.height - view.height
        if (heightDiff > dpToPx(KEYBOARD_HEIGHT_APPROXIMATELY)) {
            listener.onKeyboardOpened()
        } else {
            listener.onKeyboardClosed()
        }
    }

    private fun dpToPx(valueInDp: Float): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics)
    }

    interface OnKeyboardListener {
        fun onKeyboardOpened()
        fun onKeyboardClosed()

    }

}

fun View.listenOnSoftKeyboard(mainActivity: MainActivity) {
    this.viewTreeObserver.addOnGlobalLayoutListener(SoftKeyboardDetector(mainActivity, this, mainActivity))
}
