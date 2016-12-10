package com.sakuna63.tumbin

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

fun ViewGroup.children(): List<View> =
        when (childCount) {
            0 -> emptyList<View>()
            else -> (0..childCount - 1).map { this.getChildAt(it) }
        }

fun String.toHtml(): Spanned = toHtml(null)


@Suppress("DEPRECATION")
fun String.toHtml(imageGetter: Html.ImageGetter?): Spanned =
        when (Build.VERSION.SDK_INT) {
            in 0..Build.VERSION_CODES.N -> Html.fromHtml(this, imageGetter, null)
            else -> Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY, imageGetter, null)
        }


fun Any.log() {
    Log.d("Tumbin", this.toString())
}

fun <E : Any> E.weak() = WeakReference(this)
