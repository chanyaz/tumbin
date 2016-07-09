package com.sakuna63.tumbin.application.di.module;

import android.support.annotation.NonNull;

import com.sakuna63.tumbin.application.contract.PostContract;
import com.sakuna63.tumbin.application.di.scope.FragmentScope;
import com.sakuna63.tumbin.data.datasource.PostsDataSource;
import com.sakuna63.tumbin.data.repository.PostsRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class PostPresenterModule {

    private final PostContract.View view;
    private final long postId;

    public PostPresenterModule(@NonNull PostContract.View view, long postId) {
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
    PostContract.View view() {
        return view;
    }

    @Provides
    @FragmentScope
    PostsDataSource postsDataSource(PostsRepository postsRepository) {
        return postsRepository;
    }
}
