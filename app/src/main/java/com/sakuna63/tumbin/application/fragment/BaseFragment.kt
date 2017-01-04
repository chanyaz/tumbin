package com.sakuna63.tumbin.application.fragment

import android.os.Bundle
import com.hannesdorfmann.fragmentargs.FragmentArgs
import com.sakuna63.tumbin.application.activity.BaseActivity
import com.sakuna63.tumbin.application.di.component.DaggerFragmentComponent
import com.sakuna63.tumbin.application.di.module.FragmentModule
import com.trello.rxlifecycle.components.support.RxFragment

abstract class BaseFragment : RxFragment() {

    internal val component by lazy {
        DaggerFragmentComponent.builder()
                .activityComponent(activityComponent)
                .fragmentModule(fragmentModule)
                .build()
    }

    internal val activityComponent by lazy {
        if (activity !is BaseActivity) {
            throw IllegalStateException(
                    "The activity of this fragment must inherit BaseActivity")
        }
        (activity as BaseActivity).activityComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentArgs.inject(this)
    }


    private val fragmentModule by lazy {
        FragmentModule(this)
    }
}
