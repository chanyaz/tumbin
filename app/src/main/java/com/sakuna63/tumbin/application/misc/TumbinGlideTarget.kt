package com.sakuna63.tumbin.application.misc

import android.widget.ImageView

import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget

class TumbinGlideTarget(view: ImageView,
                        private val autoPlayGif: Boolean) : GlideDrawableImageViewTarget(view) {

    override fun onResourceReady(resource: GlideDrawable,
                                 animation: GlideAnimation<in GlideDrawable>?) {
        super.onResourceReady(resource, animation)
        if (!autoPlayGif) {
            resource.stop()
        }
    }

    override fun onStop() {
        if (autoPlayGif) {
            super.onStop()
        }
    }

    override fun onStart() {
        if (autoPlayGif) {
            super.onStart()
        }
    }
}
