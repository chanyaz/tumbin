package com.sakuna63.tumbin.application.util;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.sakuna63.tumbin.application.di.module.ApiModule;
import com.sakuna63.tumbin.data.model.AltSize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;


public final class PostUtils {

    public static final String EXTENSION_GIF = ".gif";

    private PostUtils() {
    }

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

    @IntDef({
            AvatarSize.SIZE_16, AvatarSize.SIZE_24, AvatarSize.SIZE_32, AvatarSize.SIZE_40,
            AvatarSize.SIZE_48, AvatarSize.SIZE_64, AvatarSize.SIZE_96, AvatarSize.SIZE_128,
            AvatarSize.SIZE_512
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AvatarSize {
        int SIZE_16 = 16;
        int SIZE_24 = 24;
        int SIZE_32 = 32;
        int SIZE_40 = 40;
        int SIZE_48 = 48;
        int SIZE_64 = 64;
        int SIZE_96 = 96;
        int SIZE_128 = 128;
        int SIZE_512 = 512;
    }
}
