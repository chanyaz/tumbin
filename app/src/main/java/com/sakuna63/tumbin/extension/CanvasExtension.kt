package com.sakuna63.tumbin.extension

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build

fun Canvas.drawRoundRectCompat(left: Int, top: Int, right: Int, bottom: Int,
                               rx: Float, ry: Float, paint: Paint) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        drawRoundRect(RectF(left.toFloat(), top.toFloat(),
                right.toFloat(), bottom.toFloat()), rx, ry, paint)
    } else {
        drawRoundRect(left.toFloat(), top.toFloat(),
                right.toFloat(), bottom.toFloat(), rx, ry, paint)
    }
}
