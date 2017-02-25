package com.sakuna63.tumbin.application.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.contract.LoginContract
import com.sakuna63.tumbin.application.contract.presenter.LoginPresenter
import com.sakuna63.tumbin.application.di.component.ActivityComponent
import com.sakuna63.tumbin.application.di.component.DaggerLoginComponent
import com.sakuna63.tumbin.application.di.component.LoginComponent
import com.sakuna63.tumbin.application.di.module.LoginPresenterModule
import com.sakuna63.tumbin.application.fragment.LoginFragment
import com.sakuna63.tumbin.application.util.FragmentUtils
import com.sakuna63.tumbin.databinding.ActivityLoginBinding
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    @Inject
    lateinit internal var presenter: LoginPresenter

    lateinit private var component: LoginComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)

        val fragment = LoginFragment.newInstance()
        initInjector(fragment)

        val fm = supportFragmentManager
        if (!FragmentUtils.doesFragmentExist(fm, LoginFragment.TAG)) {
            FragmentUtils.addFragment(fm, fragment, R.id.container_fragment, LoginFragment.TAG)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val fm = supportFragmentManager
        val fragment = fm.findFragmentByTag(LoginFragment.TAG) as LoginFragment
        fragment.onNewIntent(intent)
    }

    override val activityComponent: ActivityComponent
        get() = component

    private fun initInjector(view: LoginContract.View) {
        component = DaggerLoginComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(activityModule)
                .loginPresenterModule(LoginPresenterModule(view))
                .build()
        component.inject(this)
    }
}
