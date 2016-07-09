package com.sakuna63.tumbin.application.di.module;

import com.sakuna63.tumbin.application.contract.PostsContract;
import com.sakuna63.tumbin.application.di.scope.ActivityScope;
import com.sakuna63.tumbin.data.datasource.DashboardDataSource;
import com.sakuna63.tumbin.data.repository.DashboardRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class PostsPresenterModule {

    private final PostsContract.View view;

    public PostsPresenterModule(PostsContract.View view) {
        this.view = view;
    }

    @Provides
    PostsContract.View view() {
        return this.view;
    }

    @Provides
    @ActivityScope
    DashboardDataSource postDataSource(DashboardRepository dashboardRepository) {
        return dashboardRepository;
    }
}
