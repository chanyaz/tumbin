package com.sakuna63.tumblrin.application.di.component;

import android.content.Context;

import com.sakuna63.tumblrin.application.di.module.ActivityModule;
import com.sakuna63.tumblrin.application.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
}
