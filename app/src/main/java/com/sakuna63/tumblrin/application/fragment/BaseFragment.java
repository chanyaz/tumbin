package com.sakuna63.tumblrin.application.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.sakuna63.tumblrin.application.activity.BaseActivity;
import com.sakuna63.tumblrin.application.di.component.ActivityComponent;
import com.sakuna63.tumblrin.application.di.component.DaggerFragmentComponent;
import com.sakuna63.tumblrin.application.di.component.FragmentComponent;
import com.sakuna63.tumblrin.application.di.module.FragmentModule;
import com.trello.rxlifecycle.components.support.RxFragment;

public abstract class BaseFragment extends RxFragment {

    private FragmentComponent component;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initInjector();
    }

    ActivityComponent getActivityComponent() {
        final FragmentActivity activity = getActivity();
        if (!(activity instanceof BaseActivity)) {
            throw new IllegalStateException(
                    "The activity of this fragment must inherit BaseActivity"
            );
        }

        return ((BaseActivity) activity).getActivityComponent();
    }

    FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    private void initInjector() {
        component = DaggerFragmentComponent.builder()
                .activityComponent(getActivityComponent())
                .fragmentModule(getFragmentModule())
                .build();
        component.inject(this);
    }
}
