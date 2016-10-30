package com.sakuna63.tumbin.application.util

import com.sakuna63.tumbin.application.di.module.ApiModule
import com.sakuna63.tumbin.data.model.Avatar


object PostUtils {
    @JvmStatic
    fun getBlogIdentifier(blogName: String?): String = "$blogName.tumblr.com"

    @JvmStatic
    fun getBlogAvatarUrl(blogIdentifier: String?, @Avatar.Size size: Long): String =
            "${ApiModule.BASE_URL}/v2/blog/$blogIdentifier/avatar/$size"
}
