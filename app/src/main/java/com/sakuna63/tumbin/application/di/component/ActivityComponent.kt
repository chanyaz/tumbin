package com.sakuna63.tumbin.application.di.component

import com.sakuna63.tumbin.application.activity.DashboardPostActivity
import com.sakuna63.tumbin.application.di.module.ActivityModule
import com.sakuna63.tumbin.application.di.scope.ActivityScope
import com.sakuna63.tumbin.data.api.TumblrService

import dagger.Component
import io.realm.RealmConfiguration

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(activity: DashboardPostActivity)

    fun realmConfiguration(): RealmConfiguration

    fun tumblrService(): TumblrService
}
