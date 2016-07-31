package com.sakuna63.tumblrin.application.di.module;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.sakuna63.tumblrin.application.di.qualifier.NetworkInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class DebugApiModule extends ApiModule {

    @Provides
    @Singleton
    @NetworkInterceptor
    @IntoSet
    Interceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    @NetworkInterceptor
    @IntoSet
    Interceptor stethoInterceptor() {
        return new StethoInterceptor();
    }

}
