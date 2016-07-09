package com.sakuna63.tumbin.application.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.sakuna63.tumbin.application.di.scope.FragmentScope;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private final RxFragment fragment;

    public FragmentModule(RxFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    Fragment fragment() {
        return fragment;
    }

    @Provides
    @FragmentScope
    Context context() {
        return fragment.getContext();
    }

    @Provides
    @FragmentScope
    FragmentManager fragmentManager() {
        return fragment.getFragmentManager();
    }

    @Provides
    @FragmentScope
    LifecycleTransformer lifecycleTransformer() {
        return fragment.bindToLifecycle();
    }
}
