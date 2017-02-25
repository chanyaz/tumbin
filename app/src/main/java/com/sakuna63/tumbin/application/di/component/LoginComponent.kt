package com.sakuna63.tumbin.application.di.component

import com.sakuna63.tumbin.application.activity.LoginActivity
import com.sakuna63.tumbin.application.di.module.ActivityModule
import com.sakuna63.tumbin.application.di.module.LoginPresenterModule
import com.sakuna63.tumbin.application.di.scope.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class, LoginPresenterModule::class))
interface LoginComponent : ActivityComponent {
    fun inject(activity: LoginActivity)
}
