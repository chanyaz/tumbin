package com.sakuna63.tumblrin.application.fragment;

import android.support.v4.app.FragmentActivity;

import com.sakuna63.tumblrin.application.activity.BaseActivity;
import com.sakuna63.tumblrin.application.di.component.ActivityComponent;
import com.sakuna63.tumblrin.application.di.module.FragmentModule;
import com.trello.rxlifecycle.components.support.RxFragment;

public abstract class BaseFragment extends RxFragment {

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
}
