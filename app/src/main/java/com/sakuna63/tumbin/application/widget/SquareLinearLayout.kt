package com.sakuna63.tumbin.application.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class SquareLinearLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    @SuppressWarnings("SuspiciousNameCombination")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width, width)
    }
}
