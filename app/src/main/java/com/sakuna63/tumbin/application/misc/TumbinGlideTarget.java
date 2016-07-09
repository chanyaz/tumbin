package com.sakuna63.tumbin.application.misc;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class TumbinGlideTarget extends GlideDrawableImageViewTarget {

    private final boolean autoPlayGif;

    public TumbinGlideTarget(ImageView view, boolean autoPlayGif) {
        super(view);
        this.autoPlayGif = autoPlayGif;
    }

    @Override
    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
        super.onResourceReady(resource, animation);
        if (!autoPlayGif) {
            resource.stop();
        }
    }

    @Override
    public void onStop() {
        if (autoPlayGif) {
            super.onStop();
        }
    }

    @Override
    public void onStart() {
        if (autoPlayGif) {
            super.onStart();
        }
    }
}
