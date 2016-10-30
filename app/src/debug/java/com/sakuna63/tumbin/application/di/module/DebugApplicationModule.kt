package com.sakuna63.tumbin.application.di.module

import android.app.Application

import io.realm.RealmConfiguration

class DebugApplicationModule(app: Application) : ApplicationModule(app) {

    override fun realmConfiguration(): RealmConfiguration =
            RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
}
