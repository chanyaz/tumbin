/*
 * Copyright 2015 Google Inc.
 * Copyright 2016 sakuna63
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sakuna63.tumbin.application.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.util.DisplayMetrics;

import com.sakuna63.tumbin.application.util.Compat;

public class BadgeDrawable extends Drawable {

    private static final int TEXT_SIZE = 12;    // sp
    private static final int PADDING = 4;       // dp
    private static final int CORNER_RADIUS = 2; // dp
    private static final int BACKGROUND_COLOR = Color.WHITE;
    private static final String TYPEFACE = "sans-serif-black";
    private static final int TYPEFACE_STYLE = Typeface.NORMAL;
    private final Paint paint;
    private final Bitmap bitmap;
    private final int width;
    private final int height;

    public BadgeDrawable(Context context, String label) {
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        final float density = dm.density;
        final float scaledDensity = dm.scaledDensity;
        final TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint
                .SUBPIXEL_TEXT_FLAG);
        textPaint.setTypeface(Typeface.create(TYPEFACE, TYPEFACE_STYLE));
        textPaint.setTextSize(TEXT_SIZE * scaledDensity);

        final float padding = PADDING * density;
        final float cornerRadius = CORNER_RADIUS * density;
        final Rect textBounds = new Rect();
        textPaint.getTextBounds(label, 0, label.length(), textBounds);
        height = (int) (padding + textBounds.height() + padding);
        width = (int) (padding + textBounds.width() + padding);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setHasAlpha(true);
        final Canvas canvas = new Canvas(bitmap);
        final Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(BACKGROUND_COLOR);
        Compat.drawRoundRect(canvas, 0, 0, width, height, cornerRadius, cornerRadius,
                backgroundPaint);
        textPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawText(label, padding, height - padding, textPaint);
        paint = new Paint();
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(bitmap, getBounds().left, getBounds().top, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        // ignored
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
