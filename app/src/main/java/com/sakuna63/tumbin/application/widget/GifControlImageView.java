package com.sakuna63.tumbin.application.widget;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class GifControlImageView extends ImageView {
    private boolean isRunnable;

    public GifControlImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isRunnable() {
        return isRunnable;
    }

    public void setRunnable(boolean runnable) {
        isRunnable = runnable;

        Drawable drawable = getDrawable();
        if (drawable == null || !(drawable instanceof Animatable)) {
            return;
        }

        if (runnable) {
            ((Animatable) drawable).start();
        } else {
            ((Animatable) drawable).stop();
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (!(drawable instanceof Animatable)) {
            return;
        }

        Animatable animDrawable = (Animatable) drawable;
        if (isRunnable) {
            animDrawable.start();
        } else {
            animDrawable.stop();
        }
    }
}
