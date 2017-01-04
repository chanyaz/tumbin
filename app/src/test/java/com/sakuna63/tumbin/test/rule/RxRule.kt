package com.sakuna63.tumbin.test.rule

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import rx.Scheduler
import rx.android.plugins.RxAndroidPlugins
import rx.android.plugins.RxAndroidSchedulersHook
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RxRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                AndroidSchedulers.reset()
                RxAndroidPlugins.getInstance().reset()
                RxAndroidPlugins.getInstance().registerSchedulersHook(object : RxAndroidSchedulersHook() {
                    override fun getMainThreadScheduler(): Scheduler {
                        return Schedulers.immediate()
                    }
                })
                //
                //                RxJavaHooks.reset();
                //                RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
                //                    @Override
                //                    public Scheduler call(Scheduler scheduler) {
                //                        return Schedulers.immediate();
                //                    }
                //                });

                base.evaluate()
            }
        }
    }
}
