package com.sakuna63.tumbin.application.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.sakuna63.tumbin.application.di.qualifier.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class DebugApiModule : ApiModule() {

//    @Provides
//    @Singleton
//    override fun okHttpClient(consumer: OAuthConsumer): OkHttpClient =
//            super.okHttpClient(consumer).newBuilder()
//                    .addNetworkInterceptor(httpLoggingInterceptor())
//                    .addNetworkInterceptor(stethoInterceptor())
//                    .build()

    @Provides
    @Singleton
    @NetworkInterceptor
    @IntoSet
    fun httpLoggingInterceptor(): Interceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    @NetworkInterceptor
    @IntoSet
    fun stethoInterceptor(): Interceptor = StethoInterceptor()
}
