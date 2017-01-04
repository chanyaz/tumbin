package com.sakuna63.tumbin.application.contract.presenter

import com.sakuna63.tumbin.application.contract.TextPostContract
import com.sakuna63.tumbin.data.dao.DashboardRealmDao
import com.sakuna63.tumbin.data.dao.RealmResultsWrapper
import com.sakuna63.tumbin.data.model.Post

class TextPostPresenter(
        private val postId: Long,
        private val view: TextPostContract.View,
        private val dashboardRealmDao: DashboardRealmDao
) : TextPostContract.Presenter {

    private val realmResultsWrapper: RealmResultsWrapper<Post> by lazy {
        dashboardRealmDao.findById(postId)
    }

    override fun init() {
        view.showPost(realmResultsWrapper.results)
    }

    override fun destroy() {
        realmResultsWrapper.close()
    }
}
