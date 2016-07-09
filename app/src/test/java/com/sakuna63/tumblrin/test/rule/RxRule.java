package com.sakuna63.tumblrin.test.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

public class RxRule implements TestRule {
    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                AndroidSchedulers.reset();
                RxAndroidPlugins.getInstance().reset();
                RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
                    @Override
                    public Scheduler getMainThreadScheduler() {
                        return Schedulers.immediate();
                    }
                });
//
//                RxJavaHooks.reset();
//                RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
//                    @Override
//                    public Scheduler call(Scheduler scheduler) {
//                        return Schedulers.immediate();
//                    }
//                });

                base.evaluate();
            }
        };
    }
}
