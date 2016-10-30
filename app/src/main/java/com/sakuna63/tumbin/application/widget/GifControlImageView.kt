package com.sakuna63.tumbin.application.widget

import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView

class GifControlImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {
    var isRunnable: Boolean = false
        set(runnable) {
            field = runnable

            val drawable = drawable
            if (drawable == null || drawable !is Animatable) {
                return
            }

            if (runnable) {
                drawable.start()
            } else {
                drawable.stop()
            }
        }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        if (drawable !is Animatable) {
            return
        }

        if (this.isRunnable) {
            drawable.start()
        } else {
            drawable.stop()
        }
    }
}
