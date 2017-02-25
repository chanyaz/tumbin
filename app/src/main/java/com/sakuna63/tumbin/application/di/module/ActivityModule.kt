package com.sakuna63.tumbin.application.di.module

import com.sakuna63.tumbin.application.di.scope.ActivityScope
import com.trello.rxlifecycle.LifecycleProvider
import com.trello.rxlifecycle.android.ActivityEvent
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: RxAppCompatActivity) {
    @Provides
    @ActivityScope
    fun LifecycleProvider(): LifecycleProvider<ActivityEvent> = activity
}
