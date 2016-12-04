package com.sakuna63.tumbin.application.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import oauth.signpost.OAuthConsumer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class DebugApiModule : ApiModule() {

    override fun okHttpClient(consumer: OAuthConsumer): OkHttpClient {
        val builder = super.okHttpClient(consumer).newBuilder()
        return addDebugInterceptors(builder).build()
    }

    override fun unsafeOkHttpClient(): OkHttpClient {
        val builder = super.unsafeOkHttpClient().newBuilder()
        return addDebugInterceptors(builder).build()
    }

    private fun addDebugInterceptors(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder
                .addNetworkInterceptor(StethoInterceptor())
                .addNetworkInterceptor(buildHttpLoggingInterceptor())
    }

    private fun buildHttpLoggingInterceptor() =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}
