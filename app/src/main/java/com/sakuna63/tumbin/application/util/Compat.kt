package com.sakuna63.tumbin.application.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build

object Compat {

    fun drawRoundRect(canvas: Canvas, left: Int, top: Int, right: Int, bottom: Int,
                      rx: Float, ry: Float, paint: Paint) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(RectF(left.toFloat(), top.toFloat(),
                    right.toFloat(), bottom.toFloat()), rx, ry, paint)
        } else {
            canvas.drawRoundRect(left.toFloat(), top.toFloat(),
                    right.toFloat(), bottom.toFloat(), rx, ry, paint)
        }
    }
}
