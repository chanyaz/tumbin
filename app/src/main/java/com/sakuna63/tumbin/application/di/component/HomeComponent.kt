package com.sakuna63.tumbin.application.di.component

import com.sakuna63.tumbin.application.activity.HomeActivity
import com.sakuna63.tumbin.application.di.module.ActivityModule
import com.sakuna63.tumbin.application.di.module.PostsPresenterModule
import com.sakuna63.tumbin.application.di.scope.ActivityScope

import dagger.Component

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ActivityModule::class, PostsPresenterModule::class))
interface HomeComponent : ActivityComponent {
    fun inject(activity: HomeActivity)
}
