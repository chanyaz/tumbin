package com.sakuna63.tumbin.application.contract;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sakuna63.tumbin.data.model.Post;

import java.util.List;

import io.realm.RealmResults;

public interface PostsContract {
    interface View extends BaseView<Presenter> {
        void showPosts(@NonNull List<Post> posts);

        void hidePosts();

        void showError(@Nullable String message);

        void hideError();

        void showEmpty();

        void hideEmpty();

        void showLoading();

        void hideLoading();

        void showPostDetail(@NonNull Post post);

        void scrollTo(long postId, int offsetPx);

        int getScrollY();
    }

    interface Presenter extends BasePresenter {
        void onPostClick(@NonNull Post post);

        void onReloadClick();

        void onRefresh();

        void onScrollBottom();
    }
}
