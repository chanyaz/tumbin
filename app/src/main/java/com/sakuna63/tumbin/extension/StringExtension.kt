package com.sakuna63.tumbin.extension

import android.os.Build
import android.text.Html

@Suppress("DEPRECATION")
fun String.toHtml(imageGetter: Html.ImageGetter?) = when (Build.VERSION.SDK_INT) {
    in 0..Build.VERSION_CODES.N -> Html.fromHtml(this, imageGetter, null)
    else -> Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY, imageGetter, null)
}


