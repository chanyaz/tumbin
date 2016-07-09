package com.sakuna63.tumbin.application.util;

import android.annotation.SuppressLint;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public final class FragmentUtils {

    private FragmentUtils() {
    }

    public static boolean doesFragmentExist(@NonNull FragmentManager fm, @Nullable String tag) {
        return fm.findFragmentByTag(tag) != null;
    }

    public static void addFragment(@NonNull FragmentManager fm, @NonNull Fragment fragment,
                                   @IdRes int containerId, String tag) {
        @SuppressLint("CommitTransaction")
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(containerId, fragment, tag);
        ft.commitNow();
    }
}
