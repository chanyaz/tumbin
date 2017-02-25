package com.sakuna63.tumbin.application.di.module

import com.sakuna63.tumbin.application.di.scope.FragmentScope
import com.trello.rxlifecycle.LifecycleProvider
import com.trello.rxlifecycle.android.FragmentEvent
import com.trello.rxlifecycle.components.support.RxFragment
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: RxFragment) {
    @Provides
    @FragmentScope
    fun lifecycleProvider(): LifecycleProvider<FragmentEvent> = fragment
}
