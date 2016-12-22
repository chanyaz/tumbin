package com.sakuna63.tumbin.extensions

import android.util.Log
import java.lang.ref.WeakReference

fun Any.log() {
    Log.d("Tumbin", this.toString())
}

fun <E : Any> E.weak() = WeakReference(this)
