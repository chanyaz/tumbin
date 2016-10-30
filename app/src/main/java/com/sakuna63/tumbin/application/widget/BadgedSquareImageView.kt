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
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.Gravity
import com.sakuna63.tumbin.R

class BadgedSquareImageView(context: Context, attrs: AttributeSet) : SquareImageView(context, attrs) {
    private val badgeGravity: Int
    private val badgePadding: Int
    private var badgeBoundsSet = false
    private var badges: List<Drawable> = listOf()
    private var badgeLabels: List<String> = listOf()

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BadgedSquareImageView, 0, 0)
        badgeGravity = a.getInt(R.styleable.BadgedSquareImageView_badgeGravity, Gravity.END or Gravity.BOTTOM)
        badgePadding = a.getDimensionPixelSize(R.styleable.BadgedSquareImageView_badgePadding, 0)
        a.recycle()

    }

    fun setBadgeColor(@ColorInt color: Int) {
        if (badges.isEmpty()) {
            return
        }
        badges.forEach { it.setColorFilter(color, PorterDuff.Mode.SRC_IN) }
    }

    fun setBadgeLabels(labels: List<String>?) {
        // TODO: check behavior
        if (badgeLabels == labels) {
            return
        }

        val labels = labels ?: mutableListOf()
        badges = labels.map { BadgeDrawable(context, it) }
        badgeLabels = labels
        badgeBoundsSet = false
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (badges.isEmpty()) {
            return
        }

        if (!badgeBoundsSet) {
            layoutBadge(badges)
        }
        badges.forEach { it.draw(canvas) }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (badges.isNotEmpty()) {
            layoutBadge(badges)
        }
    }

    private fun layoutBadge(badges: List<Drawable>) {
        val preBounds = Rect()
        badges.forEachIndexed { i, badge ->
            val badgeBounds = badge.bounds
            val offsetX = Math.abs(preBounds.left - preBounds.right)
            Gravity.apply(badgeGravity,
                    badge.intrinsicWidth,
                    badge.intrinsicHeight,
                    Rect(0, 0, width, height),
                    badgePadding * (i + 1) + offsetX,
                    badgePadding,
                    badgeBounds)
            badge.bounds = badgeBounds
        }
        badgeBoundsSet = true
    }
}
