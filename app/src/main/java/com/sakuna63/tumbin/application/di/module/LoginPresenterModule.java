package com.sakuna63.tumbin.application.di.module;

import com.sakuna63.tumbin.application.contract.LoginContract;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginPresenterModule {

    private final LoginContract.View view;

    public LoginPresenterModule(LoginContract.View view) {
        this.view = view;
    }

    @Provides
    LoginContract.View view() {
        return this.view;
    }
}
