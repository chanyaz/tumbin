package com.sakuna63.tumbin.application.contract.presenter;

import android.support.annotation.NonNull;

import com.sakuna63.tumbin.application.contract.PostContract;
import com.sakuna63.tumbin.application.di.scope.FragmentScope;
import com.sakuna63.tumbin.data.dao.DashboardRealmDao;
import com.sakuna63.tumbin.data.dao.RealmResultsWrapper;
import com.sakuna63.tumbin.data.model.Post;

import javax.inject.Inject;

@FragmentScope
public class DashboardPostPresenter implements PostContract.Presenter {

    private final long postId;

    @NonNull
    private final PostContract.View view;

    @NonNull
    private final DashboardRealmDao dashboardRealmDao;

    private RealmResultsWrapper<Post> realmResultsWrapper;

    @Inject
    public DashboardPostPresenter(long postId, @NonNull PostContract.View view,
                                  @NonNull DashboardRealmDao dashboardRealmDao) {
        this.postId = postId;
        this.view = view;
        this.dashboardRealmDao = dashboardRealmDao;
    }

    @Inject
    public void setupView() {
        view.setPresenter(this);
    }

    @Override
    public void init() {
        realmResultsWrapper = dashboardRealmDao.findById(postId);
        view.showPost(realmResultsWrapper.getResults());
    }

    @Override
    public void destroy() {
        realmResultsWrapper.close();
    }
}
