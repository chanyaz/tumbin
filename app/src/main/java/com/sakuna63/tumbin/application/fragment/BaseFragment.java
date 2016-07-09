package com.sakuna63.tumbin.application.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.sakuna63.tumbin.application.activity.BaseActivity;
import com.sakuna63.tumbin.application.di.component.ActivityComponent;
import com.sakuna63.tumbin.application.di.component.DaggerFragmentComponent;
import com.sakuna63.tumbin.application.di.component.FragmentComponent;
import com.sakuna63.tumbin.application.di.module.FragmentModule;
import com.trello.rxlifecycle.components.support.RxFragment;

public abstract class BaseFragment extends RxFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

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
        @SuppressWarnings("deprecation")
        FragmentComponent component = DaggerFragmentComponent.builder()
                .activityComponent(getActivityComponent())
                .fragmentModule(getFragmentModule())
                .build();
        component.inject(this);
    }
}
