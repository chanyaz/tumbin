package com.sakuna63.tumbin.application.di.component

import com.sakuna63.tumbin.DebugApp
import com.sakuna63.tumbin.application.di.module.ApiModule
import com.sakuna63.tumbin.application.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, ApiModule::class))
interface DebugApplicationComponent : ApplicationComponent {
    fun inject(app: DebugApp)
}
