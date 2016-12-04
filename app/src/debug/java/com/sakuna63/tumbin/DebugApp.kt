package com.sakuna63.tumbin

import com.facebook.stetho.Stetho
import com.sakuna63.tumbin.application.di.module.DebugApiModule
import com.sakuna63.tumbin.application.di.module.DebugApplicationModule
import com.sakuna63.tumbin.application.di.component.DaggerDebugApplicationComponent
import com.sakuna63.tumbin.application.misc.AccountManager
import com.sakuna63.tumbin.data.model.Token
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import javax.inject.Inject

class DebugApp : App() {

    @Inject
    lateinit internal var accountManager: AccountManager

    override fun onCreate() {
        super.onCreate()

        initStetho()
        initInjector()
        initToken()
    }

    private fun initToken() {
        val debugToken = Token(BuildConfig.TOKEN_DEBUG, BuildConfig.TOKEN_SECRET_DEBUG)
        accountManager.saveToken(debugToken)
    }

    private fun initInjector() {
        val component = DaggerDebugApplicationComponent.builder()
                .applicationModule(DebugApplicationModule(this))
                .apiModule(DebugApiModule())
                .build()
        component.inject(this)
        appComponent = component
    }

    private fun initStetho() {
        val initializer = Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build()
        Stetho.initialize(initializer)
    }
}
