package com.sakuna63.tumbin.extensions

import android.app.Activity
import android.support.annotation.IdRes
import android.view.View

@Suppress("UNCHECKED_CAST")
fun <E : View> Activity.bindView(@IdRes resId: Int): Lazy<E> = lazy {
    this.findViewById(resId) as E
}