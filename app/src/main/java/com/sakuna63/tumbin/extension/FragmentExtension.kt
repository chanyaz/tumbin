package com.sakuna63.tumbin.extension

import android.os.Bundle
import android.support.v4.app.Fragment

fun Fragment.applyArguments(block: Bundle.() -> Unit): Fragment {
    val args = arguments ?: Bundle()
    block(args)
    return this.apply { arguments = args }
}
