package com.sakuna63.tumbin

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.GlideModule
import okhttp3.OkHttpClient
import java.io.InputStream

class OkHttp3GlideModule : GlideModule {

    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
    }

    override fun registerComponents(context: Context?, glide: Glide?) {
        if (context == null) return
        glide?.register(GlideUrl::class.java, InputStream::class.java,
                OkHttpUrlLoader.Factory(getClient(context)))
    }

    private fun getClient(context: Context): OkHttpClient {
        val app = context.applicationContext as App
        return app.appComponent.okHttpClient()
    }

}

