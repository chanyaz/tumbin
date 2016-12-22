package com.sakuna63.tumbin.application.util

import android.text.Html
import com.sakuna63.tumbin.application.di.module.ApiModule
import com.sakuna63.tumbin.data.model.Avatar
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.extensions.toHtml


object PostUtils {
    @JvmStatic
    fun getBlogIdentifier(blogName: String?): String = "$blogName.tumblr.com"

    @JvmStatic
    fun getBlogAvatarUrl(blogIdentifier: String?, @Avatar.Size size: Long): String =
            "${ApiModule.BASE_URL}/v2/blog/$blogIdentifier/avatar/$size"

    // TODO: enhance html decoration
    @JvmStatic
    fun getFormattedBody(body: String?, @Post.Format format: String,
                         imageGetter: Html.ImageGetter): CharSequence? {
        if (body == null) { return body }
        return when (format) {
            Post.FORMAT_PLAIN -> body
            Post.FORMAT_HTML -> body.toHtml(imageGetter)
            Post.FORMAT_MARKDOWN -> body.toHtml(imageGetter)
            else -> throw IllegalArgumentException("Unknown post format: " + format)
        }
    }
}
