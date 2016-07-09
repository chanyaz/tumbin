package com.sakuna63.tumbin.application.di.module;

import android.support.annotation.NonNull;

import com.sakuna63.tumbin.application.contract.PostsContract;
import com.sakuna63.tumbin.application.di.scope.ActivityScope;
import com.sakuna63.tumbin.data.datasource.PostsDataSource;
import com.sakuna63.tumbin.data.repository.PostsRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class PostsPresenterModule {

    private final PostsContract.View view;

    public PostsPresenterModule(@NonNull PostsContract.View view) {
        this.view = view;
    }

    @Provides
    PostsContract.View view() {
        return this.view;
    }

    @Provides
    @ActivityScope
    PostsDataSource postsDataSource(PostsRepository postsRepository) {
        return postsRepository;
    }
}
