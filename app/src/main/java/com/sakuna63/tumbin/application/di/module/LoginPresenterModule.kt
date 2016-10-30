package com.sakuna63.tumbin.application.di.module

import com.sakuna63.tumbin.application.contract.LoginContract

import dagger.Module
import dagger.Provides

@Module
class LoginPresenterModule(private val view: LoginContract.View) {

    @Provides
    fun view(): LoginContract.View = this.view
}
