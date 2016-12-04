package com.sakuna63.tumbin.application.di.component

import com.sakuna63.tumbin.App
import com.sakuna63.tumbin.application.contract.presenter.login.OauthHelper
import com.sakuna63.tumbin.application.di.module.ApiModule
import com.sakuna63.tumbin.application.di.module.ApplicationModule
import com.sakuna63.tumbin.application.misc.AccountManager
import com.sakuna63.tumbin.data.api.TumblrService
import dagger.Component
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, ApiModule::class))
interface ApplicationComponent {
    fun okhttpClient(): OkHttpClient

    fun accountManger(): AccountManager

    fun oauthHelper(): OauthHelper

    fun realmConfiguration(): RealmConfiguration

    fun tumblrService(): TumblrService
}
