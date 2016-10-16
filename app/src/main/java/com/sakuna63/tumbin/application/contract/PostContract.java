package com.sakuna63.tumbin.application.contract;

import android.support.annotation.NonNull;

import com.sakuna63.tumbin.data.model.Post;

public interface PostContract {
    interface View extends BaseView<Presenter> {
        void showPost(@NonNull Post post);

        void startAnimationGif();

        void stopAnimationGif();
    }

    interface Presenter extends BasePresenter {
        void onLikeClick();

        void onReblogClick();
    }
}
