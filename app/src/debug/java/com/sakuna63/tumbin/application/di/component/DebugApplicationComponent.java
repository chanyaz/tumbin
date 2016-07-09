package com.sakuna63.tumbin.application.di.component;

import com.sakuna63.tumbin.DebugApp;
import com.sakuna63.tumbin.application.di.module.ApplicationModule;
import com.sakuna63.tumbin.application.di.module.AuthenticationModule;
import com.sakuna63.tumbin.application.di.module.DebugApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, AuthenticationModule.class, DebugApiModule.class})
public interface DebugApplicationComponent extends ApplicationComponent {
    void inject(DebugApp app);
}
