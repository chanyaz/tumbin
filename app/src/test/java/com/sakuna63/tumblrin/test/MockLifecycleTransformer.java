package com.sakuna63.tumblrin.test;

import com.trello.rxlifecycle.LifecycleTransformer;

import javax.annotation.Nonnull;

import rx.Completable;
import rx.Single;

public class MockLifecycleTransformer implements LifecycleTransformer {
    @Nonnull
    @Override
    public Single.Transformer forSingle() {
        return new Single.Transformer() {
            @Override
            public Object call(Object o) {
                return o;
            }
        };
    }

    @Nonnull
    @Override
    public Completable.Transformer forCompletable() {
        return new Completable.Transformer() {
            @Override
            public Completable call(Completable completable) {
                return completable;
            }
        };
    }

    @Override
    public Object call(Object o) {
        return o;
    }
}
