package com.sakuna63.tumblrin.application.contract.presenter;

import android.support.annotation.NonNull;

import com.sakuna63.tumblrin.application.contract.PostsContract;
import com.sakuna63.tumblrin.application.di.scope.ActivityScope;
import com.sakuna63.tumblrin.data.model.Post;
import com.sakuna63.tumblrin.data.repository.PostRepository;
import com.trello.rxlifecycle.LifecycleTransformer;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

@ActivityScope
public class PostsPresenter implements PostsContract.Presenter {

    @NonNull
    private final PostsContract.View view;

    @NonNull
    private final PostRepository repository;

    @NonNull
    final Realm realm;

    @NonNull
    private final LifecycleTransformer lifecycleTransformer;

    public PostsPresenter(@NonNull PostsContract.View view,
                          @NonNull PostRepository repository,
                          @NonNull RealmConfiguration realmConfiguration,
                          @NonNull LifecycleTransformer lifecycleTransformer) {
        this.view = view;
        this.repository = repository;
        this.realm = Realm.getInstance(realmConfiguration);
        this.lifecycleTransformer = lifecycleTransformer;
    }

    @Inject
    void setupView() {
        this.view.setPresenter(this);
    }

    @Override
    public void init() {
        loadingPost();
    }

    private void loadingPost() {

    }

    private void showPosts(RealmResults<Post> posts) {
        view.hideLoading();
        view.hideEmpty();
        view.hideError();
        view.showPosts(posts);
    }

    private void showEmpty() {
        view.hideLoading();
        view.hideError();
        view.hidePosts();
        view.showEmpty();
    }

    @Override
    public void onPostClick(Post post) {

    }

    @Override
    public void onReloadClick() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onScrollBottom() {

    }
}
