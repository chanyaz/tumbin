package com.sakuna63.tumbin.extensions

import android.annotation.SuppressLint
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager


@SuppressLint("CommitTransaction")
fun Fragment.addToContainer(fm: FragmentManager, @IdRes containerId: Int, tag: String?) =
        fm.beginTransaction()
                .add(containerId, this, tag)
                .commitNow()

