package com.sakuna63.tumbin.application.di.module

import android.app.Application
import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration

@Module
open class ApplicationModule(private val app: Application) {

    @Provides
    fun context(): Context = app

    @Provides
    @Singleton
    open fun realmConfiguration(): RealmConfiguration = RealmConfiguration.Builder().build()
}
