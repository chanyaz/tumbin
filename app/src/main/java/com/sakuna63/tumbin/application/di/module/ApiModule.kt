package com.sakuna63.tumbin.application.di.module

import android.annotation.SuppressLint
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.sakuna63.tumbin.application.di.qualifier.NetworkInterceptor
import com.sakuna63.tumbin.data.api.TumblrService
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import oauth.signpost.OAuthConsumer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Module(includes = arrayOf(AuthenticationModule::class))
open class ApiModule {

    @Provides
    @Singleton
    fun tumblrService(retrofit: Retrofit): TumblrService =
            retrofit.create(TumblrService::class.java)

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient, objectMapper: ObjectMapper): Retrofit =
            Retrofit.Builder()
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                    .baseUrl(BASE_URL)
                    .build()

    @Provides
    @Singleton
    open fun okHttpClient(consumer: OAuthConsumer): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(signingInterceptor(consumer))
                    .build()

    /**
     * https://futurestud.io/tutorials/glide-module-example-accepting-self-signed-https-certificates
     * https://github.com/fs-opensource/android-tutorials-picasso/blob/master/PicassoTutorial/app/src/main/java/io/futurestud/tutorials/picasso/okhttp/UnsafeOkHttpClient.java
     */
    @Provides
    @Singleton
    @Named(NAME_UNSAFE_CLIENT)
    open fun unsafeOkHttpClient(): OkHttpClient {
        // Create a trust manager that does not validate certificate chains
        val unsafeTrustManager = createUnsafeTrustManager()

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf(unsafeTrustManager), null)

        // Create an ssl socket factory with our all-trusting manager
        return OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, unsafeTrustManager)
                .hostnameVerifier { hostName, sslSession -> true }
                .build()
    }

    fun createUnsafeTrustManager(): X509TrustManager {
        return object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<out X509Certificate>? {
                return emptyArray()
            }
        }
    }

//    @Provides
//    @Singleton
//    fun okHttpClient(interceptors: Set<Interceptor>,
//                     @NetworkInterceptor networkInterceptors: Set<Interceptor>): OkHttpClient {
//        val builder = OkHttpClient.Builder()
//        for (interceptor in interceptors) {
//            builder.addInterceptor(interceptor)
//        }
//        for (interceptor in networkInterceptors) {
//            builder.addNetworkInterceptor(interceptor)
//        }
//        return builder.build()
//    }

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
    fun signingInterceptor(consumer: OAuthConsumer): Interceptor =
            SigningInterceptor(consumer as OkHttpOAuthConsumer)

    @Provides
    @NetworkInterceptor
    @ElementsIntoSet
    @Singleton
    fun dummyInterceptor(): Set<Interceptor> = emptySet()

    @SuppressLint("SimpleDateFormat")
    @Provides
    @Singleton
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        objectMapper.dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")
        return objectMapper
    }

    companion object {
        private val BASE_URL = "https://api.tumblr.com"
        const val NAME_UNSAFE_CLIENT = "ApiModule.NAME_UNSAFE_CLIENT"
    }
}
