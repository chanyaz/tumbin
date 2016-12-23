package com.sakuna63.tumbin.application.databinding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.misc.GlideImageGetter
import com.sakuna63.tumbin.application.misc.TumbinGlideTarget
import com.sakuna63.tumbin.application.widget.BadgedSquareImageView
import com.sakuna63.tumbin.data.model.Photo
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.data.model.PostBody

@BindingAdapter("photos")
fun ViewGroup.setPhotos(photos: List<Photo>?) {
    photos?.forEach {
        val inflater = LayoutInflater.from(this.context)
        val imageView = inflater.inflate(R.layout.layout_photo, this, false) as ImageView
        this.addView(imageView)
        imageView.setImageByUrl(it.altSizes[0].url, null, false)
    }
}

@BindingAdapter("badgeTexts")
fun BadgedSquareImageView.setBadgeTexts(badgeTexts: List<String>?) {
    this.setBadgeLabels(badgeTexts)
}

@BindingAdapter(value = *arrayOf("srcUrl", "placeHolder", "autoPlayGif"), requireAll = false)
fun ImageView.setImageByUrl(url: String?, placeHolder: Drawable?, autoPlayGif: Boolean) {
    Glide.with(this.context)
            .load(url)
            .crossFade()
            .placeholder(placeHolder)
            .into(TumbinGlideTarget(this, autoPlayGif))
}

@BindingAdapter(value = *arrayOf("textBody", "format"), requireAll = false)
fun TextView.setTextBody(body: PostBody, @Post.Format format: String) {
    val imageGetter = GlideImageGetter(this)
    this.text = body.format(format, imageGetter)
}
