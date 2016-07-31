package com.sakuna63.tumblrin.application.di.module;

import com.sakuna63.tumblrin.application.contract.PostsContract;
import com.sakuna63.tumblrin.application.di.scope.ActivityScope;
import com.sakuna63.tumblrin.data.datasource.PostDataSource;
import com.sakuna63.tumblrin.data.repository.PostRepository;

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
    PostDataSource postDataSource(PostRepository postRepository) {
        return postRepository;
    }
}
