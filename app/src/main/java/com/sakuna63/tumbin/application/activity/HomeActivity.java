package com.sakuna63.tumbin.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.f2prateek.dart.HensonNavigable;
import com.sakuna63.tumbin.R;
import com.sakuna63.tumbin.application.contract.PostsContract;
import com.sakuna63.tumbin.application.contract.presenter.DashboardPresenter;
import com.sakuna63.tumbin.application.di.component.ActivityComponent;
import com.sakuna63.tumbin.application.di.component.DaggerHomeComponent;
import com.sakuna63.tumbin.application.di.component.HomeComponent;
import com.sakuna63.tumbin.application.di.module.PostsPresenterModule;
import com.sakuna63.tumbin.application.fragment.PostsFragment;
import com.sakuna63.tumbin.application.fragment.PostsFragmentBuilder;
import com.sakuna63.tumbin.application.misc.AccountManager;
import com.sakuna63.tumbin.application.util.ActivityUtils;

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

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        if (toolBar != null) {
            setSupportActionBar(toolBar);
        }

        FragmentManager fm = getSupportFragmentManager();
        PostsFragment fragment = (PostsFragment) fm.findFragmentByTag(PostsFragment.TAG);
        if (fragment == null) {
            fragment = new PostsFragmentBuilder().build();
            ActivityUtils.addFragment(fm, fragment, R.id.container_fragment, PostsFragment.TAG);
        }

        initInjector(fragment);

        if (!accountManager.isLoggedIn()) {
            Intent intent = Henson.with(this).gotoLoginActivity().build();
            startActivity(intent);
            finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reload:
                presenter.onReloadClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
