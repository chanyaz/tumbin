package com.sakuna63.tumbin.application.fragment

import android.os.Bundle
import com.hannesdorfmann.fragmentargs.FragmentArgs
import com.sakuna63.tumbin.application.activity.BaseActivity
import com.sakuna63.tumbin.application.di.component.ActivityComponent
import com.sakuna63.tumbin.application.di.component.DaggerFragmentComponent
import com.sakuna63.tumbin.application.di.module.FragmentModule
import com.trello.rxlifecycle.components.support.RxFragment

abstract class BaseFragment : RxFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentArgs.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initInjector()
    }

    internal val activityComponent: ActivityComponent
        get() {
            if (activity !is BaseActivity) {
                throw IllegalStateException(
                        "The activity of this fragment must inherit BaseActivity")
            }
            return (activity as BaseActivity).activityComponent
        }

    internal val fragmentModule: FragmentModule
        get() = FragmentModule(this)

    private fun initInjector() {
        @SuppressWarnings("deprecation")
        val component = DaggerFragmentComponent.builder()
                .activityComponent(activityComponent)
                .fragmentModule(fragmentModule)
                .build()
        component.inject(this)
    }
}
