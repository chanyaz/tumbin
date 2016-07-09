package com.sakuna63.tumbin.application.util;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.sakuna63.tumbin.application.di.module.ApiModule;
import com.sakuna63.tumbin.data.model.AltSize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

public class PostUtils {

    public static final String EXTENSION_GIF = ".gif";
    public static final int SIZE_16 = 16;
    public static final int SIZE_24 = 24;
    public static final int SIZE_32 = 32;
    public static final int SIZE_40 = 40;
    public static final int SIZE_48 = 48;
    public static final int SIZE_64 = 64;
    public static final int SIZE_96 = 96;
    public static final int SIZE_128 = 128;
    public static final int SIZE_512 = 512;

    public static String getBlogIdentifier(String blogName) {
        return String.format(Locale.US, "%s.tumblr.com", blogName);
    }

    public static String getBlogAvatarUrl(String blogIdentifier, @AvatarSize int size) {
        return String.format(Locale.US,
                ApiModule.BASE_URL + "/v2/blog/%s/avatar/%d", blogIdentifier, size);
    }

    public static boolean isGifPhoto(@NonNull AltSize altSize) {
        return altSize.url.endsWith(EXTENSION_GIF);
    }

    @IntDef({SIZE_16, SIZE_24, SIZE_32, SIZE_40, SIZE_48, SIZE_64, SIZE_96, SIZE_128, SIZE_512})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AvatarSize {
    }
}
