package com.sakuna63.tumbin.application.util

import android.text.TextUtils
import android.view.View

object DataBindingUtils {
    @JvmStatic
    fun goneIfEmpty(str: String?): Int {
        return if (TextUtils.isEmpty(str)) View.GONE else View.VISIBLE
    }

    @JvmStatic
    fun goneIfNull(obj: Any?): Int {
        return if (obj == null) View.GONE else View.VISIBLE
    }

    @JvmStatic
    fun goneIfZero(value: Int): Int {
        return if (value == 0) View.GONE else View.VISIBLE
    }
}
