package com.sakuna63.tumbin.application.di.component

import com.sakuna63.tumbin.application.di.module.ActivityModule
import com.sakuna63.tumbin.application.di.module.ApiModule
import com.sakuna63.tumbin.application.di.module.ApplicationModule
import com.sakuna63.tumbin.application.di.module.LoginPresenterModule
import com.sakuna63.tumbin.application.di.module.PostsPresenterModule
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, ApiModule::class))
interface ApplicationComponent {
    fun plus(module: ActivityModule): ActivityComponent

    fun plus(module: ActivityModule, loginModule: LoginPresenterModule): LoginComponent

    fun plus(module: ActivityModule, postsModule: PostsPresenterModule): HomeComponent

    @Named(ApiModule.NAME_UNSAFE_CLIENT)
    fun unsafeOkHttpClient(): OkHttpClient
}
