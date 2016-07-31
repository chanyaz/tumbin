package com.sakuna63.tumblrin.application.contract;

import com.sakuna63.tumblrin.data.model.Post;

import io.realm.RealmResults;

public interface PostsContract {
    interface View extends BaseView<Presenter> {
        void showPosts(RealmResults<Post> posts);

        void hidePosts();

        void showError(String message);

        void hideError();

        void showEmpty();

        void hideEmpty();

        void showLoading();

        void hideLoading();

        void showPostDetail(Post post);
    }

    interface Presenter extends BasePresenter {
        void onPostClick(Post post);

        void onReloadClick();

        void onRefresh();

        void onScrollBottom();
    }
}
