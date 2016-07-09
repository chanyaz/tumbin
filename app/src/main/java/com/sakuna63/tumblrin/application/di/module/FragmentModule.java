package com.sakuna63.tumblrin.application.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private final Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    Fragment fragment() {
        return fragment;
    }

    @Provides
    Context context() {
        return fragment.getContext();
    }

    @Provides
    FragmentManager fragmentManager() {
        return fragment.getFragmentManager();
    }
}
