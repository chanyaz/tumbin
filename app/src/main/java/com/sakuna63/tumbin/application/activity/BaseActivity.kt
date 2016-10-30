package com.sakuna63.tumbin.application.activity

import com.sakuna63.tumbin.App
import com.sakuna63.tumbin.application.di.component.ActivityComponent
import com.sakuna63.tumbin.application.di.component.ApplicationComponent
import com.sakuna63.tumbin.application.di.module.ActivityModule
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

abstract class BaseActivity : RxAppCompatActivity() {

    abstract val activityComponent: ActivityComponent

    internal val applicationComponent: ApplicationComponent
        get() = (application as App).appComponent

    internal val activityModule: ActivityModule
        get() = ActivityModule(this)
}
