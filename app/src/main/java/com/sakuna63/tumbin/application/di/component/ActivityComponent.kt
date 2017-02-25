package com.sakuna63.tumbin.application.di.component

import com.sakuna63.tumbin.application.activity.DashboardPostActivity
import com.sakuna63.tumbin.application.di.module.ActivityModule
import com.sakuna63.tumbin.application.di.module.FragmentModule
import com.sakuna63.tumbin.application.di.scope.ActivityScope
import dagger.Subcomponent
import io.realm.RealmConfiguration

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun plus(module: FragmentModule): FragmentComponent

    fun inject(activity: DashboardPostActivity)

    fun realmConfiguration(): RealmConfiguration
}
