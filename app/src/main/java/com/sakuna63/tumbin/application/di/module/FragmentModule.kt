package com.sakuna63.tumbin.application.di.module

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.sakuna63.tumbin.application.di.scope.FragmentScope
import com.trello.rxlifecycle.LifecycleProvider
import com.trello.rxlifecycle.android.FragmentEvent
import com.trello.rxlifecycle.components.support.RxFragment
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(DaoModule::class))
class FragmentModule(private val fragment: RxFragment) {

    @Provides
    @FragmentScope
    fun fragment(): Fragment = fragment

    @Provides
    @FragmentScope
    fun context(): Context = fragment.context

    @Provides
    @FragmentScope
    fun fragmentManager(): FragmentManager = fragment.fragmentManager

    @Provides
    @FragmentScope
    fun lifecycleProvider(): LifecycleProvider<FragmentEvent> = fragment
}
