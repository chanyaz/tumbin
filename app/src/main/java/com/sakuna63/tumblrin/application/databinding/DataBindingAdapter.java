package com.sakuna63.tumblrin.application.databinding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sakuna63.tumblrin.application.misc.TumblrinGlideTarget;

public class DataBindingAdapter {


    @BindingAdapter({"srcUrl", "placeHolder"})
    public static void setImageByUrl(ImageView imageView, String url, Drawable placeHolder) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade()
                .placeholder(placeHolder)
                .into(new TumblrinGlideTarget(imageView, false));
    }
}
