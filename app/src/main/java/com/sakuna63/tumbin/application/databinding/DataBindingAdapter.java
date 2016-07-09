package com.sakuna63.tumbin.application.databinding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sakuna63.tumbin.R;
import com.sakuna63.tumbin.application.misc.TumbinGlideTarget;
import com.sakuna63.tumbin.application.widget.BadgedSquareImageView;
import com.sakuna63.tumbin.data.model.Photo;

import java.util.List;

public class DataBindingAdapter {

    @BindingAdapter({"photos"})
    public static void setPhotos(ViewGroup viewGroup, List<Photo> photos) {
        for (Photo photo : photos) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            ImageView imageView =
                    (ImageView) inflater.inflate(R.layout.layout_photo, viewGroup, false);
            viewGroup.addView(imageView);
            setImageByUrl(imageView, photo.altSizes.get(0).url);
        }
    }

    @BindingAdapter({"badgeTexts"})
    public static void setBadgeTexts(BadgedSquareImageView badgedView, List<String> badgeTexts) {
        badgedView.setBadgeLabels(badgeTexts);
    }

    @BindingAdapter({"srcUrl"})
    public static void setImageByUrl(ImageView imageView, String url) {
        setImageByUrl(imageView, url, null, false);
    }

    @BindingAdapter({"srcUrl", "placeHolder"})
    public static void setImageByUrl(ImageView imageView, String url, Drawable placeHolder) {
        setImageByUrl(imageView, url, placeHolder, false);
    }


    @BindingAdapter({"srcUrl", "placeHolder", "autoPlayGif"})
    public static void setImageByUrl(ImageView imageView, String url, Drawable placeHolder, boolean autoPlayGif) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade()
                .placeholder(placeHolder)
                .into(new TumbinGlideTarget(imageView, autoPlayGif));
    }
}
