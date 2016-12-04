package com.sakuna63.tumbin

import android.app.Application
import com.sakuna63.tumbin.application.di.component.ApplicationComponent
import com.sakuna63.tumbin.application.di.component.DaggerApplicationComponent
import com.sakuna63.tumbin.application.di.module.ApplicationModule
import io.realm.Realm
import net.danlew.android.joda.JodaTimeAndroid

open class App : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initInjector()
        JodaTimeAndroid.init(this)
        Realm.init(this)
    }

    private fun initInjector() {
        val component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        appComponent = component
    }
}
