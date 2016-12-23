package com.sakuna63.tumbin.data.model

import android.text.Html
import com.sakuna63.tumbin.application.di.module.ApiModule
import com.sakuna63.tumbin.extensions.toHtml

typealias BlogName = String
fun BlogName.identifier() = "$this.tumblr.com"

typealias PostBody = String
fun PostBody.format(@Post.Format format: String, imageGetter: Html.ImageGetter) =
        when (format) {
            Post.FORMAT_PLAIN -> this
            Post.FORMAT_HTML -> this.toHtml(imageGetter)
            Post.FORMAT_MARKDOWN -> this.toHtml(imageGetter)
            else -> throw IllegalArgumentException("Unknown post format: " + format)
        }

typealias AvatarSize = Int
fun AvatarSize.url(blogName: BlogName) =
        "${ApiModule.BASE_URL}/v2/blog/${blogName.identifier()}/avatar/$this"
