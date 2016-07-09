package com.sakuna63.tumbin.application.di.component;

import com.sakuna63.tumbin.application.di.module.FragmentModule;
import com.sakuna63.tumbin.application.di.module.PostPresenterModule;
import com.sakuna63.tumbin.application.di.scope.FragmentScope;
import com.sakuna63.tumbin.application.fragment.DashboardPostFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = ActivityComponent.class,
        modules = {FragmentModule.class, PostPresenterModule.class})
public interface DashboardPostComponent extends FragmentComponent {
    void inject(DashboardPostFragment fragment);
}
