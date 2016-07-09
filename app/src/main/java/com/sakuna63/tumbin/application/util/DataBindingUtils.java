package com.sakuna63.tumbin.application.util;

import android.text.TextUtils;
import android.view.View;

public class DataBindingUtils {
    public static int goneIfEmpty(String str) {
        return TextUtils.isEmpty(str) ? View.GONE : View.VISIBLE;
    }

    public static int goneIfNull(Object obj) {
        return obj == null ? View.GONE : View.VISIBLE;
    }

    public static int goneIfZero(int value) {
        return value == 0 ? View.GONE : View.VISIBLE;
    }
}
