package com.sakuna63.tumbin.extension

import android.databinding.ViewStubProxy
import android.view.View

fun <T> ViewStubProxy.inflateIfNeeded(): T {
    if (!isInflated) {
        viewStub.inflate()
    }
    @Suppress("UNCHECKED_CAST")
    return binding as T
}

fun ViewStubProxy.goneRootIfInflated() {
    if (isInflated) {
        root.visibility = View.GONE
    }
}