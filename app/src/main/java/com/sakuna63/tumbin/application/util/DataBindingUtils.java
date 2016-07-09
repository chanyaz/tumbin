package com.sakuna63.tumbin.application.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

public class DataBindingUtils {
    public static int goneIfEmpty(@Nullable String str) {
        return TextUtils.isEmpty(str) ? View.GONE : View.VISIBLE;
    }

    public static int goneIfNull(@Nullable Object obj) {
        return obj == null ? View.GONE : View.VISIBLE;
    }

    public static int goneIfZero(int value) {
        return value == 0 ? View.GONE : View.VISIBLE;
    }
}
