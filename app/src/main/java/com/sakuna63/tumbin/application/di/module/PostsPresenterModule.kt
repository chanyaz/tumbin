package com.sakuna63.tumbin.application.di.module

import com.sakuna63.tumbin.application.contract.PostsContract
import com.sakuna63.tumbin.application.di.scope.ActivityScope
import com.sakuna63.tumbin.data.datasource.PostsDataSource
import com.sakuna63.tumbin.data.repository.PostsRepository

import dagger.Module
import dagger.Provides

@Module
class PostsPresenterModule(private val view: PostsContract.View) {

    @Provides
    fun view(): PostsContract.View = view

    @Provides
    @ActivityScope
    fun postsDataSource(postsRepository: PostsRepository): PostsDataSource = postsRepository
}
