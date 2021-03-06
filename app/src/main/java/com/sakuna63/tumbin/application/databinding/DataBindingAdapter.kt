package com.sakuna63.tumbin.application.databinding

import android.content.Context
import android.content.pm.PackageManager
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.ExoPlayerLibraryInfo
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.misc.GlideImageGetter
import com.sakuna63.tumbin.application.misc.TumbinGlideTarget
import com.sakuna63.tumbin.application.util.PostUtils
import com.sakuna63.tumbin.application.widget.BadgedSquareImageView
import com.sakuna63.tumbin.data.model.Photo
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.databinding.LayoutPhotoBinding

@BindingAdapter("activated")
fun View.setActivated(activated: Boolean) {
    isActivated = activated
}

@BindingAdapter("enabled")
fun View.setEnabled(enabled: Boolean) {
    isEnabled = enabled
}

@BindingAdapter("selected")
fun View.setSelected(selected: Boolean) {
    isSelected = selected
}

@BindingAdapter("photos")
fun ViewGroup.setPhotos(photos: List<Photo>?) {
    photos?.forEach {
        val binding = LayoutPhotoBinding.inflate(LayoutInflater.from(this.context), this, true)
        binding.height = it.altSizes[0].height
        binding.width = it.altSizes[0].width
        binding.srcUrl = it.altSizes[0].url
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
fun TextView.setTextBody(body: String, @Post.Format format: String) {
    val imageGetter = GlideImageGetter(this)
    this.text = PostUtils.getFormattedBody(body, format, imageGetter)
}

@BindingAdapter("aspectRatio")
fun SimpleExoPlayerView.setAspectRatio(aspectRatio: Float) {
    (findViewById(R.id.exo_content_frame) as AspectRatioFrameLayout).setAspectRatio(aspectRatio)
}

@BindingAdapter(value = *arrayOf("videoUrl", "volume", "loop"), requireAll = false)
fun SimpleExoPlayerView.setPlayer(videoUrl: String?, volume: Float?, loop: Boolean = false) {
    player = player ?: buildPlayer(context)
    if (videoUrl != null) player.prepare(buildVideoSource(videoUrl, loop))
    if (volume != null) player.volume = volume
}


private fun SimpleExoPlayerView.buildVideoSource(videoUrl: String?, loop: Boolean): MediaSource {
    val bandwidthMeter = DefaultBandwidthMeter()
    val hoge = DefaultHttpDataSourceFactory(getUserAgent(context, "Tumbin"), bandwidthMeter)
    val dataSourceFactory = DefaultDataSourceFactory(context, bandwidthMeter, hoge)
    val extractorsFactory = DefaultExtractorsFactory()
    val videoSource = ExtractorMediaSource(Uri.parse(videoUrl),
            dataSourceFactory, extractorsFactory, null, null)

    if (loop) {
        return LoopingMediaSource(videoSource)
    }
    return videoSource
}

private fun buildPlayer(context: Context): SimpleExoPlayer {
    val bandwidthMeter = DefaultBandwidthMeter()
    val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
    val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
    val loadControl = DefaultLoadControl()

    return ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl)
}

/**
 * Returns a user agent string based on the given application name and the library version.
 *
 * @param context A valid context of the calling application.
 * @param applicationName String that will be prefix'ed to the generated user agent.
 * @return A user agent string generated using the applicationName and the library version.
 */
fun getUserAgent(context: Context, applicationName: String): String {
    var versionName: String
    try {
        val packageName = context.packageName
        val info = context.packageManager.getPackageInfo(packageName, 0)
        versionName = info.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        versionName = "?"
    }

    return applicationName + "/" + versionName + " (Linux;Android " + Build.VERSION.RELEASE + ") " + "ExoPlayerLib/" + ExoPlayerLibraryInfo.VERSION
}
