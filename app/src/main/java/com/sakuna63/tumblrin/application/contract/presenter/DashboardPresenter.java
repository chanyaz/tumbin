package com.sakuna63.tumblrin.application.contract.presenter;

import android.support.annotation.NonNull;

import com.sakuna63.tumblrin.application.contract.PostsContract;
import com.sakuna63.tumblrin.application.di.scope.ActivityScope;
import com.sakuna63.tumblrin.data.dao.PostDao;
import com.sakuna63.tumblrin.data.dao.RealmResultsWrapper;
import com.sakuna63.tumblrin.data.datasource.PostDataSource;
import com.sakuna63.tumblrin.data.model.Post;
import com.sakuna63.tumblrin.util.CloseableUtils;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.realm.RealmResults;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ActivityScope
public class DashboardPresenter implements PostsContract.Presenter {

    private static final Integer MAX_LIMIT = 20;

    @NonNull
    private final PostsContract.View view;

    @NonNull
    private final PostDataSource dataSource;

    @NonNull
    private final PostDao postDao;

    @NonNull
    private final LifecycleTransformer transformer;

    private RealmResultsWrapper<Post> realmResultsWrapper;
    private boolean isLoading;
    private boolean hasMorePost = true;

    @Inject
    public DashboardPresenter(@NonNull PostsContract.View view,
                              @NonNull PostDataSource dataSource,
                              @NonNull PostDao postDao,
                              @NonNull LifecycleTransformer lifecycleTransformer) {
        this.view = view;
        this.dataSource = dataSource;
        this.postDao = postDao;
        transformer = lifecycleTransformer;
    }

    @Inject
    void setupView() {
        this.view.setPresenter(this);
    }

    @Override
    public void init() {
        realmResultsWrapper = postDao.findByType(Post.POST_TYPE_PHOTO);
        RealmResults<Post> posts = realmResultsWrapper.getResults();

        if (posts.isEmpty()) {
            loadingPost(0, null);
            showLoading();
            return;
        }

        showPosts(posts);
    }

    public void destroy() {
        CloseableUtils.close(realmResultsWrapper);
    }

    private void loadingPost(Integer offset, final Long sinceId) {
        isLoading = true;

        //noinspection unchecked
        dataSource.getDashboard(MAX_LIMIT, offset, sinceId)
                .compose(transformer.forSingle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleSubscriber<List<Post>>() {
                    @Override
                    public void onSuccess(List<Post> posts) {
                        isLoading = false;

                        boolean isRefreshRequest = sinceId != null;
                        if (isRefreshRequest) {
                            showPosts(realmResultsWrapper.getResults());
                            return;
                        }

                        hasMorePost = posts.size() == MAX_LIMIT;
                        if (hasMorePost) {
                            showPosts(realmResultsWrapper.getResults());
                            return;
                        }

                        if (posts.isEmpty() && realmResultsWrapper.getResults().isEmpty()) {
                            showEmpty();
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        isLoading = false;
                        showError(error);
                    }
                });
    }

    @Override
    public void onPostClick(Post post) {
        view.showPostDetail(post);
    }

    @Override
    public void onReloadClick() {
        loadingPost(0, null);
        showLoading();
    }

    @Override
    public void onRefresh() {
        loadingPost(null, realmResultsWrapper.getResults().get(0).getId());
        showLoading();
    }

    @Override
    public void onScrollBottom() {
        if (isLoading || !hasMorePost) {
            return;
        }
        loadingPost(realmResultsWrapper.getResults().size() + 1, null);
        showLoading();
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
}
