package com.sakuna63.tumbin.application.widget

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.Region
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat

class DrawableWrapper(drawable: Drawable?) : Drawable(), Drawable.Callback {

    var wrappedDrawable = drawable
        set(drawable) {
            wrappedDrawable?.callback = null
            field = drawable
            drawable?.callback = this
        }

    override fun draw(canvas: Canvas) {
        wrappedDrawable?.draw(canvas)
    }

    override fun onBoundsChange(bounds: Rect) {
        wrappedDrawable?.bounds = bounds
    }

    override fun setChangingConfigurations(configs: Int) {
        wrappedDrawable?.changingConfigurations = configs
    }

    override fun getChangingConfigurations(): Int {
        return wrappedDrawable?.changingConfigurations ?: super.getChangingConfigurations()
    }

    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun setDither(dither: Boolean) {
        wrappedDrawable?.setDither(dither)
    }

    override fun setFilterBitmap(filter: Boolean) {
        wrappedDrawable?.isFilterBitmap = filter
    }

    override fun setAlpha(alpha: Int) {
        wrappedDrawable?.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        wrappedDrawable?.colorFilter = cf
    }

    override fun isStateful(): Boolean {
        return wrappedDrawable?.isStateful ?: super.isStateful()
    }

    override fun setState(stateSet: IntArray): Boolean {
        return wrappedDrawable?.setState(stateSet) ?: super.setState(stateSet)
    }

    override fun getState(): IntArray {
        return wrappedDrawable?.state ?: super.getState()
    }

    override fun jumpToCurrentState() {
        DrawableCompat.jumpToCurrentState(wrappedDrawable ?: this)
    }

    override fun getCurrent(): Drawable {
        return wrappedDrawable?.current ?: super.getCurrent()
    }

    override fun setVisible(visible: Boolean, restart: Boolean): Boolean {
        val result = super.setVisible(visible, restart)
        return if (result) result else wrappedDrawable?.setVisible(visible, restart) ?: result
    }

    override fun getOpacity(): Int {
        return wrappedDrawable?.opacity ?: PixelFormat.TRANSPARENT
    }

    override fun getTransparentRegion(): Region? {
        return wrappedDrawable?.transparentRegion ?: super.getTransparentRegion()
    }

    override fun getIntrinsicWidth(): Int {
        return wrappedDrawable?.intrinsicWidth ?: super.getIntrinsicWidth()
    }

    override fun getIntrinsicHeight(): Int {
        return wrappedDrawable?.intrinsicHeight ?: super.getIntrinsicHeight()
    }

    override fun getMinimumWidth(): Int {
        return wrappedDrawable?.minimumWidth ?: super.getMinimumWidth()
    }

    override fun getMinimumHeight(): Int {
        return wrappedDrawable?.minimumHeight ?: super.getMinimumHeight()
    }

    override fun getPadding(padding: Rect): Boolean {
        return wrappedDrawable?.getPadding(padding) ?: super.getPadding(padding)
    }

    /**
     * {@inheritDoc}
     */
    override fun invalidateDrawable(who: Drawable) {
        invalidateSelf()
    }

    /**
     * {@inheritDoc}
     */
    override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
        scheduleSelf(what, `when`)
    }

    /**
     * {@inheritDoc}
     */
    override fun unscheduleDrawable(who: Drawable, what: Runnable) {
        unscheduleSelf(what)
    }

    override fun onLevelChange(level: Int): Boolean {
        return wrappedDrawable?.setLevel(level) ?: super.setLevel(level)
    }

    override fun setAutoMirrored(mirrored: Boolean) {
        DrawableCompat.setAutoMirrored(wrappedDrawable ?: this, mirrored)
    }

    override fun isAutoMirrored(): Boolean {
        return DrawableCompat.isAutoMirrored(wrappedDrawable ?: this)
    }

    override fun setTint(tint: Int) {
        DrawableCompat.setTint(wrappedDrawable ?: this, tint)
    }

    override fun setTintList(tint: ColorStateList?) {
        DrawableCompat.setTintList(wrappedDrawable ?: this, tint)
    }

    override fun setTintMode(tintMode: PorterDuff.Mode) {
        DrawableCompat.setTintMode(wrappedDrawable ?: this, tintMode)
    }

    override fun setHotspot(x: Float, y: Float) {
        DrawableCompat.setHotspot(wrappedDrawable ?: this, x, y)
    }

    override fun setHotspotBounds(left: Int, top: Int, right: Int, bottom: Int) {
        DrawableCompat.setHotspotBounds(wrappedDrawable ?: this, left, top, right, bottom)
    }
}
