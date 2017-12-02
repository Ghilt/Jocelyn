package se.agency.adccor.jocelyn.views

import android.app.FragmentManager
import se.agency.adccor.jocelyn.R

/**
 * Created by adamn on 11/24/2017.
 */

fun FragmentManager.getChatFragment() = findFragmentById(R.id.fragmentChat) as ChatFragment