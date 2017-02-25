package com.sakuna63.tumbin.application.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.contract.LoginContract
import com.sakuna63.tumbin.application.contract.presenter.LoginPresenter
import com.sakuna63.tumbin.application.di.component.ActivityComponent
import com.sakuna63.tumbin.application.di.component.LoginComponent
import com.sakuna63.tumbin.application.di.module.LoginPresenterModule
import com.sakuna63.tumbin.application.fragment.LoginFragment
import com.sakuna63.tumbin.databinding.ActivityLoginBinding
import com.sakuna63.tumbin.extension.addFragment
import com.sakuna63.tumbin.extension.hasFragment
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

        if (!hasFragment(LoginFragment.TAG)) {
            addFragment(fragment, R.id.container_fragment, LoginFragment.TAG)
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
        component = applicationComponent.plus(activityModule, LoginPresenterModule(view))
        component.inject(this)
    }
}
