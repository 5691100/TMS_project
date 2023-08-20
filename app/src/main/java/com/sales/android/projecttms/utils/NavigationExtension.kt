package com.sales.android.projecttms.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

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