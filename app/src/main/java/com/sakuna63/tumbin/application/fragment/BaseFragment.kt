package com.sakuna63.tumbin.application.fragment

import com.sakuna63.tumbin.application.activity.BaseActivity
import com.sakuna63.tumbin.application.di.module.FragmentModule
import com.trello.rxlifecycle.components.support.RxFragment

abstract class BaseFragment : RxFragment() {

    internal val component by lazy {
        activityComponent.plus(fragmentModule)
    }

    internal val activityComponent by lazy {
        if (activity !is BaseActivity) {
            throw IllegalStateException(
                    "The activity of this fragment must inherit BaseActivity")
        }
        (activity as BaseActivity).activityComponent
    }

    private val fragmentModule by lazy {
        FragmentModule(this)
    }
}
