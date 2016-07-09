package com.sakuna63.tumbin.application.di.component;

import com.sakuna63.tumbin.application.di.module.FragmentModule;
import com.sakuna63.tumbin.application.di.scope.FragmentScope;
import com.sakuna63.tumbin.application.fragment.BaseFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = ActivityComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(BaseFragment fragment);
}
