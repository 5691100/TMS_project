package com.sales.android.projecttms.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.sales.android.projecttms.R
import com.sales.android.projecttms.ui.householdslist.HouseholdListFragment

fun FragmentManager.replaceFragment(
    idContainer: Int,
    fragment: Fragment,
    addToBackStack: Boolean
) {
    if (addToBackStack) {
        beginTransaction()
            .replace(idContainer, fragment)
            .addToBackStack("")
            .commit()
    } else {
        beginTransaction()
            .replace(idContainer, fragment)
            .commit()
    }
}

fun FragmentManager.replaceWithAnimation(
    idContainer: Int,
    fragment: Fragment
) {
    commit {
        setCustomAnimations(
            R.anim.slide_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.slide_out
        )
        replace(idContainer, fragment)
        addToBackStack(null)
    }
}

fun FragmentManager.replaceWithReverseAnimation(
    idContainer: Int,
    fragment: Fragment
) {
    commit {
        setCustomAnimations(
            R.anim.fade_in,
            R.anim.slide_out,
            R.anim.fade_in,
            R.anim.slide_out
        )
        replace(idContainer, fragment)
        addToBackStack(null)
    }
}


fun FragmentManager.addFragment(
    idContainer: Int,
    fragment: Fragment,
    addToBackStack: Boolean
) {
    if (addToBackStack) {
        beginTransaction()
            .add(idContainer, fragment)
            .addToBackStack("")
            .commit()
    } else {
        beginTransaction()
            .add(idContainer, fragment)
            .commit()
    }
}