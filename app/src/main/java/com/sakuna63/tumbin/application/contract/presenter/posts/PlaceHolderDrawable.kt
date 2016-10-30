package com.sakuna63.tumbin.application.contract.presenter.posts

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

import com.sakuna63.tumbin.R

object PlaceHolderDrawable {
    @JvmStatic
    fun newInstance(context: Context, index: Int): Drawable {
        val colors = context.resources.getIntArray(R.array.post_loading_placeholders)
        return ColorDrawable(colors[index % colors.size])
    }
}
