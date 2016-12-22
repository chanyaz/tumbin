package com.sakuna63.tumbin.extensions

import android.view.View
import android.view.ViewGroup

fun ViewGroup.children(): List<View> =
        when (childCount) {
            0 -> emptyList<View>()
            else -> (0..childCount - 1).map { this.getChildAt(it) }
        }
