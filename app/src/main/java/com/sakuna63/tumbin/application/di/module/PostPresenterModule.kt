package com.sakuna63.tumbin.application.di.module

import com.sakuna63.tumbin.application.contract.PostContract
import com.sakuna63.tumbin.application.di.scope.FragmentScope
import com.sakuna63.tumbin.data.datasource.PostDataSource
import com.sakuna63.tumbin.data.repository.PostRepository

import dagger.Module
import dagger.Provides

@Module
class PostPresenterModule(private val view: PostContract.View, private val postId: Long) {

    @Provides
    @FragmentScope
    fun postId(): Long = postId

    @Provides
    @FragmentScope
    fun view(): PostContract.View = view

    @Provides
    @FragmentScope
    fun postsDataSource(postsRepository: PostRepository): PostDataSource = postsRepository
}
