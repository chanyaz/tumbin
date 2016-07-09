package com.sakuna63.tumbin.application.di.module;

import android.annotation.SuppressLint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.sakuna63.tumbin.application.di.qualifier.NetworkInterceptor;
import com.sakuna63.tumbin.data.api.TumblrService;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Set;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import dagger.multibindings.IntoSet;
import oauth.signpost.OAuthConsumer;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

@Module
public class ApiModule {

    public static final String BASE_URL = "https://api.tumblr.com";

    @SuppressLint("SimpleDateFormat")
    @Provides
    @Singleton
    public static ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"));
        return objectMapper;
    }

    @Provides
    @Singleton
    TumblrService tumblrService(Retrofit retrofit) {
        return retrofit.create(TumblrService.class);
    }

    @Provides
    @Singleton
    Retrofit retrofit(OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .baseUrl(BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient okHttpClient(Set<Interceptor> interceptors,
                              @NetworkInterceptor Set<Interceptor> networkInterceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }
        for (Interceptor interceptor : networkInterceptors) {
            builder.addNetworkInterceptor(interceptor);
        }
        return builder.build();
    }

//    @Provides
//    @IntoSet
//    @Singleton
//    Interceptor apiKeyAttachingInterceptor() {
//        return new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                HttpUrl.Builder builder = chain.request().url().newBuilder();
//                HttpUrl url = builder.addQueryParameter("api_key", BuildConfig.CONSUMER_KEY).build();
//
//                Request request = chain.request()
//                        .newBuilder()
//                        .url(url)
//                        .build();
//
//                return chain.proceed(request);
//            }
//        };
//    }

    @Provides
    @IntoSet
    @Singleton
    Interceptor signingInterceptor(OAuthConsumer consumer) {
        return new SigningInterceptor((OkHttpOAuthConsumer) consumer);
    }

    @Provides
    @NetworkInterceptor
    @ElementsIntoSet
    @Singleton
    Set<Interceptor> dummyInterceptor() {
        return Collections.emptySet();
    }
}
