package com.sakuna63.tumbin.application.util

import android.databinding.ViewStubProxy
import android.view.View

object ViewStubProxyUtils {

    fun <T> inflate(stubProxy: ViewStubProxy): T {
        if (!stubProxy.isInflated) {
            stubProxy.viewStub.inflate()
        }
        //noinspection unchecked
        return stubProxy.binding as T
    }


    fun goneRootIfInflated(stubProxy: ViewStubProxy) {
        if (stubProxy.isInflated) {
            stubProxy.root.visibility = View.GONE
        }
    }
}
