package com.sakuna63.tumbin.application.contract.presenter

import com.sakuna63.tumbin.application.contract.PostContract
import com.sakuna63.tumbin.application.di.scope.FragmentScope
import com.sakuna63.tumbin.data.dao.DashboardRealmDao
import com.sakuna63.tumbin.data.dao.RealmResultsWrapper
import com.sakuna63.tumbin.data.model.Post

import javax.inject.Inject

@FragmentScope
class DashboardPostPresenter
@Inject
constructor(private val postId: Long, private val view: PostContract.View,
            private val dashboardRealmDao: DashboardRealmDao) : PostContract.Presenter {

    private val realmResultsWrapper: RealmResultsWrapper<Post> by lazy {
        dashboardRealmDao.findById(postId)
    }

    @Inject
    fun setupView() {
        view.setPresenter(this)
    }

    override fun init() {
        view.showPost(realmResultsWrapper.results)
    }

    override fun destroy() {
        realmResultsWrapper.close()
    }
}
