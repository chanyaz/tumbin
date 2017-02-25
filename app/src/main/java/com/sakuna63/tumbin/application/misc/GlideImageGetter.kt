package com.sakuna63.tumbin.application.misc

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.sakuna63.tumbin.application.widget.DrawableWrapper
import com.sakuna63.tumbin.extension.weak

class GlideImageGetter
constructor(val container: TextView) : Html.ImageGetter {

    override fun getDrawable(source: String): Drawable {
        val drawable = DrawableWrapper(null)

        Glide.with(container.context)
                .load(source)
                .into(ImageGetterTarget(container, drawable))
        return drawable
    }

    private class ImageGetterTarget(container: TextView, drawable: DrawableWrapper)
        : SimpleTarget<GlideDrawable>() {

        val containerRef = container.weak()
        val drawableRef = drawable.weak()

        override fun onResourceReady(resource: GlideDrawable,
                                     glideAnimation: GlideAnimation<in GlideDrawable>?) {
            val container = containerRef.get() ?: return
            val drawable = drawableRef.get() ?: return

            val rect = Rect(0, 0, resource.intrinsicWidth, resource.intrinsicHeight)
            resource.bounds = rect
            drawable.bounds = rect
            drawable.wrappedDrawable = resource

            // Text (= container) doesn't try to set callback to drawable.
            // So we must call container.invalidate() directly.
            container.text = container.text
            container.invalidate()

        }

    }
}