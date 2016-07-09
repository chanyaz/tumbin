package com.sakuna63.tumbin.application.util;

import android.databinding.ViewStubProxy;
import android.support.annotation.NonNull;
import android.view.View;

public final class ViewStubProxyUtils {
    private ViewStubProxyUtils() {
    }

    public static <T> T inflate(@NonNull ViewStubProxy stubProxy) {
        if (!stubProxy.isInflated()) {
            stubProxy.getViewStub().inflate();
        }
        //noinspection unchecked
        return (T) stubProxy.getBinding();
    }


    public static void goneRootIfInflated(@NonNull ViewStubProxy stubProxy) {
        if (stubProxy.isInflated()) {
            stubProxy.getRoot().setVisibility(View.GONE);
        }
    }
}
