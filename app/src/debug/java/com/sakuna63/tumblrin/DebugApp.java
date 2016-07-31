package com.sakuna63.tumblrin;

import com.facebook.stetho.Stetho;
import com.sakuna63.tumblrin.application.di.component.ApplicationComponent;
import com.sakuna63.tumblrin.application.di.component.DaggerDebugApplicationComponent;
import com.sakuna63.tumblrin.application.di.component.DebugApplicationComponent;
import com.sakuna63.tumblrin.application.di.module.ApplicationModule;
import com.sakuna63.tumblrin.application.di.module.DebugApiModule;
import com.sakuna63.tumblrin.application.misc.AccountManager;
import com.sakuna63.tumblrin.data.model.Token;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import javax.inject.Inject;

public class DebugApp extends App {

    @Inject
    AccountManager accountManager;

    @Override
    public void onCreate() {
        super.onCreate();

        initStetho();
        initInjector();
        initToken();
    }

    private void initToken() {
        Token debugToken = new Token(BuildConfig.TOKEN_DEBUG, BuildConfig.TOKEN_SECRET_DEBUG);
        accountManager.saveToken(debugToken);
    }

    private void initInjector() {
        DebugApplicationComponent component = DaggerDebugApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .debugApiModule(new DebugApiModule())
                .build();
        component.inject(this);
        setAppComponent(component);
    }

    private void initStetho() {
        Stetho.Initializer initializer = Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build();
        Stetho.initialize(initializer);
    }
}
