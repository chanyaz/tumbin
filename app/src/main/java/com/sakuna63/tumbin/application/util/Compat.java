package com.sakuna63.tumbin.application.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;

public final class Compat {

    private Compat() {
    }

    public static void drawRoundRect(@NonNull Canvas canvas, int left, int top, int right, int bottom,
                                     float rx, float ry, @NonNull Paint paint) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(new RectF(left, top, right, bottom), rx, ry, paint);
        } else {
            canvas.drawRoundRect(left, top, right, bottom, rx, ry, paint);
        }
    }
}
