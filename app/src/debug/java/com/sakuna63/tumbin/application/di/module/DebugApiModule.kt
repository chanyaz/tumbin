package com.sakuna63.tumbin.application.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import oauth.signpost.OAuthConsumer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class DebugApiModule : ApiModule() {

    override fun okHttpClient(consumer: OAuthConsumer): OkHttpClient {
        val client = super.okHttpClient(consumer)
        return client.newBuilder()
                .addNetworkInterceptor(StethoInterceptor())
                .addNetworkInterceptor(buildHttpLoggingInterceptor())
                .build()
    }

    private fun buildHttpLoggingInterceptor() =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}
