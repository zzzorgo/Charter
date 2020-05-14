package zzz.zzzorgo.charter.utils

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun showFragmentDialog(hostActivity: FragmentActivity, dialogFragment: DialogFragment) {
    val fragmentManager = hostActivity.supportFragmentManager;

    showFragmentDialog(fragmentManager, dialogFragment)
}

fun showFragmentDialog(fragmentManager: FragmentManager, dialogFragment: DialogFragment) {
    // The device is smaller, so show the fragment fullscreen
    val transaction = fragmentManager.beginTransaction()
    // For a little polish, specify a transition animation
    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    // To make it fullscreen, use the 'content' root view as the container
    // for the fragment, which is always the root view for the activity
    transaction
        .add(android.R.id.content, dialogFragment)
        .addToBackStack(null)
        .commit()
}
