package com.sakuna63.tumbin.application.util

import android.annotation.SuppressLint
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

object FragmentUtils {

    fun doesFragmentExist(fm: FragmentManager, tag: String?): Boolean {
        return fm.findFragmentByTag(tag) != null
    }

    fun addFragment(fm: FragmentManager, fragment: Fragment,
                    @IdRes containerId: Int, tag: String) {
        @SuppressLint("CommitTransaction")
        val ft = fm.beginTransaction()
        ft.add(containerId, fragment, tag)
        ft.commitNow()
    }
}
