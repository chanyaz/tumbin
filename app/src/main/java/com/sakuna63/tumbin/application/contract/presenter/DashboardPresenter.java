package com.sakuna63.tumbin.application.contract.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sakuna63.tumbin.application.contract.PostsContract;
import com.sakuna63.tumbin.application.di.scope.ActivityScope;
import com.sakuna63.tumbin.data.dao.DashboardRealmDao;
import com.sakuna63.tumbin.data.dao.RealmResultsWrapper;
import com.sakuna63.tumbin.data.datasource.DashboardDataSource;
import com.sakuna63.tumbin.data.model.Post;
import com.trello.rxlifecycle.LifecycleTransformer;

import javax.inject.Inject;

import io.realm.RealmResults;
import rx.Observable;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.media.CamcorderProfile.get;

@ActivityScope
public class DashboardPresenter implements PostsContract.Presenter {

    private static final int MAX_LIMIT = 20;
    private static final int REFRESHING_OFFSET_PX = -100;

    @NonNull
    private final PostsContract.View view;

    @NonNull
    private final DashboardDataSource dataSource;

    @NonNull
    private final DashboardRealmDao dashboardRealmDao;

    @NonNull
    private final LifecycleTransformer transformer;

    private RealmResultsWrapper<RealmResults<Post>> realmResultsWrapper;
    private boolean isLoading;
    private boolean hasMorePost = true;

    @Inject
    public DashboardPresenter(@NonNull PostsContract.View view,
                              @NonNull DashboardDataSource dataSource,
                              @NonNull DashboardRealmDao dashboardRealmDao,
                              @NonNull LifecycleTransformer lifecycleTransformer) {
        this.view = view;
        this.dataSource = dataSource;
        this.dashboardRealmDao = dashboardRealmDao;
        transformer = lifecycleTransformer;
    }

    @Inject
    void setupView() {
        this.view.setPresenter(this);
    }

    @Override
    public void init() {
        RealmResults<Post> posts = getPosts();
        if (posts.isEmpty()) {
            showLoading();
            loadingPost(0, null, null, false);
            return;
        }

        showPosts(posts);
    }

    @Override
    public void destroy() {
        realmResultsWrapper.close();
    }

    @SuppressWarnings("unchecked")
    private void loadingPost(@Nullable final Integer offset, @Nullable final Long sinceId,
                             @Nullable final Long beforeId, boolean forceRefreshCache) {
        isLoading = true;

        Observable observable = dataSource.fetchDashboard(MAX_LIMIT, offset, sinceId, beforeId).toObservable();
        if (forceRefreshCache) {
            observable = dataSource.deleteAllCache().toObservable().concatWith(observable);
        }

        observable.last().toSingle()
                .compose(transformer.forSingle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleSubscriber<Integer>() {
                    @Override
                    public void onSuccess(Integer value) {
                        isLoading = false;
                        handleDashboardCallback(sinceId, value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading = false;
                        showError(e);
                    }
                });
    }

    private void handleDashboardCallback(Long sinceId, int numOfPosts) {
        boolean isRefreshRequest = sinceId != null;
        if (isRefreshRequest) {
            showPosts(getPosts());
            // TODO: 2016/08/20 buggy
            // show glimpse of newer posts
            if (view.getScrollY() == 0) {
                view.scrollTo(sinceId, REFRESHING_OFFSET_PX);
            }
            return;
        }

        hasMorePost = numOfPosts == MAX_LIMIT;

        if (numOfPosts == 0 && getPosts().isEmpty()) {
            showEmpty();
        } else {
            showPosts(getPosts());
        }
    }

    @Override
    public void onPostClick(@NonNull Post post) {
        view.showPostDetail(post);
    }

    @Override
    public void onReloadClick() {
        showLoading();
        loadingPost(0, null, null, true);
    }

    @Override
    public void onRefresh() {
        Long latestId = getPosts().get(0).id;
        showLoading();
        loadingPost(null, latestId, null, false);
    }

    @Override
    public void onScrollBottom() {
        if (isLoading || !hasMorePost) {
            return;
        }
        showLoading();
        RealmResults<Post> posts = getPosts();
        Long oldestId = posts.get(posts.size() - 1).id;
        loadingPost(null, null, oldestId, false);
    }

    private void showPosts(RealmResults<Post> posts) {
        view.hideLoading();
        view.showPosts(posts);
    }

    private void showLoading() {
        view.hideEmpty();
        view.hideError();
        view.showLoading();
    }

    private void showEmpty() {
        view.hideLoading();
        view.showEmpty();
    }

    private void showError(Throwable e) {
        view.hideLoading();
        view.showError(e.getMessage());
    }

    @NonNull
    private RealmResults<Post> getPosts() {
        if (realmResultsWrapper == null) {
            realmResultsWrapper = dashboardRealmDao.findByType(Post.PostType.PHOTO);
        }
        return realmResultsWrapper.getResults();
    }
}
