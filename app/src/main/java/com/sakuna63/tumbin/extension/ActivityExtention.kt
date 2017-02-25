package com.sakuna63.tumbin.extension

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View

@Suppress("UNCHECKED_CAST")
fun <E : View> Activity.bindView(@IdRes resId: Int): Lazy<E> = lazy {
    this.findViewById(resId) as E
}

fun FragmentActivity.hasFragment(tag: String) =
        supportFragmentManager.findFragmentByTag(tag) != null

fun FragmentActivity.addFragment(fragment: Fragment, @IdRes containerId: Int, tag: String) =
        supportFragmentManager.beginTransaction()
                .add(containerId, fragment, tag)
                .commitNow()
