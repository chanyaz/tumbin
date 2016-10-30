package com.sakuna63.tumbin.application.di.component

import com.sakuna63.tumbin.DebugApp
import com.sakuna63.tumbin.application.di.module.ApplicationModule
import com.sakuna63.tumbin.application.di.module.AuthenticationModule
import com.sakuna63.tumbin.application.di.module.DebugApiModule

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, AuthenticationModule::class, DebugApiModule::class))
interface DebugApplicationComponent : ApplicationComponent {
    fun inject(app: DebugApp)
}
