package com.sakuna63.tumbin.application.di.module

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater

import com.sakuna63.tumbin.application.di.scope.ActivityScope
import com.trello.rxlifecycle.LifecycleTransformer
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: RxAppCompatActivity) {

    @Provides
    @ActivityScope
    fun activity(): Activity = activity

    @Provides
    @ActivityScope
    fun context(): Context = activity

    @Provides
    @ActivityScope
    fun layoutInflater(): LayoutInflater = activity.layoutInflater

    @Provides
    @ActivityScope
    fun lifecycleTransformer(): LifecycleTransformer<Any> = activity.bindToLifecycle()
}
