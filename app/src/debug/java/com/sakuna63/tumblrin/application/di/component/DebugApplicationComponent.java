package com.sakuna63.tumblrin.application.di.component;

import com.sakuna63.tumblrin.DebugApp;
import com.sakuna63.tumblrin.application.di.module.ApplicationModule;
import com.sakuna63.tumblrin.application.di.module.AuthenticationModule;
import com.sakuna63.tumblrin.application.di.module.DebugApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, AuthenticationModule.class, DebugApiModule.class})
public interface DebugApplicationComponent extends ApplicationComponent {
    void inject(DebugApp app);
}
