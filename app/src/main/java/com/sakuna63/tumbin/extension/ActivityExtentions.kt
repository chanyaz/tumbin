package com.sakuna63.tumbin.extension

import android.app.Activity
import android.os.Build
import android.support.annotation.IdRes
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.View
import android.view.ViewGroup
import java.lang.ref.WeakReference

@Suppress("UNCHECKED_CAST")
fun <E : View> Activity.bindView(@IdRes resId: Int): Lazy<E> = lazy {
    this.findViewById(resId) as E
}
