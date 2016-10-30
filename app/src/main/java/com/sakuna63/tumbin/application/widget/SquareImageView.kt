package com.sakuna63.tumbin.application.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

open class SquareImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {

    @SuppressWarnings("SuspiciousNameCombination")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width, width)
    }
}
