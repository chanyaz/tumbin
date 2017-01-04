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

package com.sakuna63.tumbin.application.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import com.sakuna63.tumbin.application.util.Compat

class BadgeDrawable(context: Context, label: String) : Drawable() {
    private val paint: Paint = Paint()
    private val bitmap: Bitmap
    private val width: Int
    private val height: Int

    init {
        val dm = context.resources.displayMetrics
        val density = dm.density
        val scaledDensity = dm.scaledDensity
        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG or Paint.SUBPIXEL_TEXT_FLAG)
        textPaint.typeface = Typeface.create(TYPEFACE, TYPEFACE_STYLE)
        textPaint.textSize = TEXT_SIZE * scaledDensity

        val padding = PADDING * density
        val cornerRadius = CORNER_RADIUS * density
        val textBounds = Rect()
        textPaint.getTextBounds(label, 0, label.length, textBounds)
        height = (padding + textBounds.height().toFloat() + padding).toInt()
        width = (padding + textBounds.width().toFloat() + padding).toInt()
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setHasAlpha(true)
        val canvas = Canvas(bitmap)
        val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundPaint.color = BACKGROUND_COLOR
        Compat.drawRoundRect(canvas, 0, 0, width, height, cornerRadius, cornerRadius,
                backgroundPaint)
        textPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        canvas.drawText(label, padding, height - padding, textPaint)
    }

    override fun getIntrinsicWidth(): Int {
        return width
    }

    override fun getIntrinsicHeight(): Int {
        return height
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, bounds.left.toFloat(), bounds.top.toFloat(), paint)
    }

    override fun setAlpha(alpha: Int) {
        // ignored
    }

    override fun setColorFilter(cf: ColorFilter?) {
        paint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    companion object {

        private val TEXT_SIZE = 12    // sp
        private val PADDING = 4       // dp
        private val CORNER_RADIUS = 2 // dp
        private val BACKGROUND_COLOR = Color.WHITE
        private val TYPEFACE = "sans-serif-black"
        private val TYPEFACE_STYLE = Typeface.NORMAL
    }
}
