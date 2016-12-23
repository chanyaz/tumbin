package com.sakuna63.tumbin.extensions

import android.databinding.ViewDataBinding
import android.databinding.ViewStubProxy
import android.view.View

fun <T : ViewDataBinding> ViewStubProxy.inflate(): T {
    if (!this.isInflated) {
        this.viewStub.inflate()
    }
    @Suppress("UNCHECKED_CAST")
    return this.binding as T
}


fun ViewStubProxy.goneRoot() {
    if (this.isInflated) {
        this.root.visibility = View.GONE
    }
}
