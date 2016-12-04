package com.sakuna63.tumbin.application.di.module

import android.app.Application
import android.content.Context
import com.sakuna63.tumbin.data.dao.DashboardRealmDao
import com.sakuna63.tumbin.data.dao.DashboardRealmDaoImpl
import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module(includes = arrayOf(DaoModule::class))
open class ApplicationModule(private val app: Application) {

    @Provides
    fun context(): Context = app

    @Provides
    @Singleton
    open fun realmConfiguration(): RealmConfiguration = RealmConfiguration.Builder().build()
}
