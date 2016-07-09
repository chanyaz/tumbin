package com.sakuna63.tumbin.application.di.module;

import com.sakuna63.tumbin.application.contract.PostContract;
import com.sakuna63.tumbin.application.contract.presenter.PostPresenter;
import com.sakuna63.tumbin.application.di.scope.FragmentScope;
import com.sakuna63.tumbin.data.datasource.DashboardDataSource;
import com.sakuna63.tumbin.data.repository.DashboardRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class PostPresenterModule {

    private final PostContract.View view;
    private final long postId;

    public PostPresenterModule(PostContract.View view, long postId) {
        this.view = view;
        this.postId = postId;
    }

    @Provides
    @FragmentScope
    long postId() {
        return postId;
    }

    @Provides
    @FragmentScope
    PostContract.Presenter presenter(PostPresenter postPresenter) {
        return postPresenter;
    }

    @Provides
    @FragmentScope
    PostContract.View view() {
        return view;
    }

    @Provides
    @FragmentScope
    DashboardDataSource postDataSource(DashboardRepository dashboardRepository) {
        return dashboardRepository;
    }
}
