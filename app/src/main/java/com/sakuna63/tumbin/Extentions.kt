package com.sakuna63.tumbin

import android.app.Activity
import android.graphics.Canvas
import android.support.annotation.IdRes
import android.util.Log
import android.view.View
import android.view.ViewGroup

fun <E : Any> MutableList<E?>.fillUntil(position: Int) {
    while (this.size <= position) {
        this.add(null)
    }
}

@Suppress("UNCHECKED_CAST")
fun <E : View> Activity.bindView(@IdRes resId: Int): Lazy<E> = lazy {
    this.findViewById(resId) as E
}

fun ViewGroup.children(): List<View> =
        when (childCount) {
            0 -> emptyList<View>()
            else -> (0..childCount - 1).map { this.getChildAt(it) }
        }

fun Any.log() {
    Log.d("Tumbin", this.toString())
}
