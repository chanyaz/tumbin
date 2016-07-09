package com.sakuna63.tumblrin.application.contract;

import com.trello.rxlifecycle.LifecycleTransformer;

public interface BasePresenter {
    void init(LifecycleTransformer transformer);
}
