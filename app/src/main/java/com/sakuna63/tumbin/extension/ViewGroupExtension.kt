package com.sakuna63.tumbin.extension

import android.view.View
import android.view.ViewGroup

fun ViewGroup.children() = when (childCount) {
    0 -> emptyList<View>()
    else -> (0..childCount - 1).map { this.getChildAt(it) }
}

