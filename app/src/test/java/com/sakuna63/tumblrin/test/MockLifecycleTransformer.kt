package com.sakuna63.tumblrin.test

import com.trello.rxlifecycle.LifecycleTransformer

import rx.Completable
import rx.Single

class MockLifecycleTransformer : LifecycleTransformer<*> {
    override fun forSingle(): Single.Transformer<*, *> {
        return object : Single.Transformer {
            override fun call(o: Any): Any {
                return o
            }
        }
    }

    override fun forCompletable(): Completable.Transformer {
        return Completable.Transformer { completable -> completable }
    }

    override fun call(o: Any): Any {
        return o
    }
}
