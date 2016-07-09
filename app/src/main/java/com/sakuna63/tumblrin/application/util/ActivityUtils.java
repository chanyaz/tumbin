package com.sakuna63.tumblrin.application.util;

import android.annotation.SuppressLint;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ActivityUtils {
    public static boolean isFragmentExist(FragmentManager fm, String tag) {
        return fm.findFragmentByTag(tag) != null;
    }

    public static void addFragment(FragmentManager fm, Fragment fragment, @IdRes int containerId, String tag) {
        @SuppressLint("CommitTransaction")
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(containerId, fragment, tag);
        ft.commitNow();
    }
}
