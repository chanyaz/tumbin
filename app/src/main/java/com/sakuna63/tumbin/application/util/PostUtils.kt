package com.sakuna63.tumbin.application.util

import android.text.Html
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.extension.toHtml


object PostUtils {
    // TODO: enhance html decoration
    // TODO: create Body class and move this method to it
    @JvmStatic
    fun getFormattedBody(body: String?, @Post.Format format: String,
                         imageGetter: Html.ImageGetter): CharSequence? {
        if (body == null) {
            return body
        }
        return when (format) {
            Post.FORMAT_PLAIN -> body
            Post.FORMAT_HTML -> body.toHtml(imageGetter)
            Post.FORMAT_MARKDOWN -> body.toHtml(imageGetter)
            else -> throw IllegalArgumentException("Unknown post format: " + format)
        }
    }
}
