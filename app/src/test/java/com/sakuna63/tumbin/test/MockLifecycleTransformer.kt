package com.sakuna63.tumbin.test

import com.trello.rxlifecycle.LifecycleTransformer

import rx.Completable
import rx.Observable
import rx.Single

class MockLifecycleTransformer : LifecycleTransformer<Any?> {
    override fun call(t: Observable<Any?>): Observable<Any?> {
        return t
    }

    override fun <U : Any?> forSingle(): Single.Transformer<U, U> {
        return Single.Transformer { t -> t }
    }

    override fun forCompletable(): Completable.Transformer {
        return Completable.Transformer { t -> t }
    }
}
