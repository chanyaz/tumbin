package com.sakuna63.tumblrin.application.di.component;

import com.sakuna63.tumblrin.application.di.module.FragmentModule;
import com.sakuna63.tumblrin.application.di.scope.FragmentScope;
import com.sakuna63.tumblrin.application.fragment.BaseFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = ActivityComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(BaseFragment fragment);
}
