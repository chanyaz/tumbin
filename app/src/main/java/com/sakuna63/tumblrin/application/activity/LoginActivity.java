package com.sakuna63.tumblrin.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.f2prateek.dart.HensonNavigable;
import com.sakuna63.tumblrin.R;
import com.sakuna63.tumblrin.application.contract.LoginContract;
import com.sakuna63.tumblrin.application.contract.presenter.LoginPresenter;
import com.sakuna63.tumblrin.application.di.component.ActivityComponent;
import com.sakuna63.tumblrin.application.di.component.DaggerLoginComponent;
import com.sakuna63.tumblrin.application.di.component.LoginComponent;
import com.sakuna63.tumblrin.application.di.module.LoginPresenterModule;
import com.sakuna63.tumblrin.application.fragment.LoginFragment;
import com.sakuna63.tumblrin.application.util.ActivityUtils;
import com.sakuna63.tumblrin.databinding.ActivityLoginBinding;

import javax.inject.Inject;

@HensonNavigable
public class LoginActivity extends BaseActivity {
    @Inject
    LoginPresenter presenter;

    private LoginComponent component;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        LoginFragment fragment = LoginFragment.newInstance();
        initInjector(fragment);

        FragmentManager fm = getSupportFragmentManager();
        if (!ActivityUtils.isFragmentExist(fm, LoginFragment.TAG)) {
            ActivityUtils.addFragment(fm, fragment, R.id.container_fragment, LoginFragment.TAG);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        FragmentManager fm = getSupportFragmentManager();
        LoginFragment fragment = (LoginFragment) fm.findFragmentByTag(LoginFragment.TAG);
        if (fragment != null) {
            fragment.onNewIntent(intent);
        }
    }

    @Override
    public ActivityComponent getActivityComponent() {
        return component;
    }

    private void initInjector(LoginContract.View view) {
        component = DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .loginPresenterModule(new LoginPresenterModule(view))
                .build();
        component.inject(this);
    }
}
