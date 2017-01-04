package com.sakuna63.tumbin.application.contract.presenter

import com.sakuna63.tumbin.application.contract.ExternalVideoPostContract
import com.sakuna63.tumbin.data.dao.DashboardRealmDao
import com.sakuna63.tumbin.data.dao.RealmResultsWrapper
import com.sakuna63.tumbin.data.model.Post

class ExternalVideoPostPresenter(
        private val postId: Long,
        private val view: ExternalVideoPostContract.View,
        private val dashboardRealmDao: DashboardRealmDao
) : ExternalVideoPostContract.Presenter {
    private val realmResultsWrapper: RealmResultsWrapper<Post> by lazy {
        dashboardRealmDao.findById(postId)
    }

    override fun init() {
        view.showPost(realmResultsWrapper.results)
    }

    override fun destroy() {
        realmResultsWrapper.close()
    }

    override fun onThumbnailClick() {
        view.openBrowser(realmResultsWrapper.results.permalinkUrl!!)
    }
}
