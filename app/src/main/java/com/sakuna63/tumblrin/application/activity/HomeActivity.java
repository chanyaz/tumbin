package com.sakuna63.tumblrin.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.f2prateek.dart.HensonNavigable;
import com.sakuna63.tumblrin.R;
import com.sakuna63.tumblrin.application.contract.PostsContract;
import com.sakuna63.tumblrin.application.contract.presenter.DashboardPresenter;
import com.sakuna63.tumblrin.application.di.component.ActivityComponent;
import com.sakuna63.tumblrin.application.di.component.DaggerHomeComponent;
import com.sakuna63.tumblrin.application.di.component.HomeComponent;
import com.sakuna63.tumblrin.application.di.module.PostsPresenterModule;
import com.sakuna63.tumblrin.application.fragment.PostsFragment;
import com.sakuna63.tumblrin.application.misc.AccountManager;
import com.sakuna63.tumblrin.application.util.ActivityUtils;

import javax.inject.Inject;

@HensonNavigable
public class HomeActivity extends BaseActivity {
    @Inject
    DashboardPresenter presenter;

    @Inject
    AccountManager accountManager;

    private HomeComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        PostsFragment fragment = PostsFragment.newInstance();
        initInjector(fragment);

        if (!accountManager.isLoggedIn()) {
            Intent intent = Henson.with(this).gotoLoginActivity().build();
            startActivity(intent);
            finish();
        }

        FragmentManager fm = getSupportFragmentManager();
        if (!ActivityUtils.isFragmentExist(fm, PostsFragment.TAG)) {
            ActivityUtils.addFragment(fm, fragment, R.id.container_fragment, PostsFragment.TAG);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public ActivityComponent getActivityComponent() {
        return component;
    }

    private void initInjector(PostsContract.View view) {
        component = DaggerHomeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .postsPresenterModule(new PostsPresenterModule(view))
                .build();
        component.inject(this);
    }
}
