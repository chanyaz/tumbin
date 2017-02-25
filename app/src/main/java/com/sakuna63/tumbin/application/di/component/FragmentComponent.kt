package com.sakuna63.tumbin.application.di.component

import com.sakuna63.tumbin.application.di.module.FragmentModule
import com.sakuna63.tumbin.application.di.scope.FragmentScope
import com.sakuna63.tumbin.application.fragment.BaseFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
    fun inject(fragment: BaseFragment)
}
