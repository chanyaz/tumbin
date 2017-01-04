package com.sakuna63.tumbin.application.contract.presenter

import com.sakuna63.tumbin.application.contract.PhotoPostContract
import com.sakuna63.tumbin.data.dao.DashboardRealmDao
import com.sakuna63.tumbin.data.dao.RealmResultsWrapper
import com.sakuna63.tumbin.data.model.Post

class PhotoPostPresenter(
        private val postId: Long,
        private val view: PhotoPostContract.View,
        private val dashboardRealmDao: DashboardRealmDao
) : PhotoPostContract.Presenter {

    private val realmResultsWrapper: RealmResultsWrapper<Post> by lazy {
        dashboardRealmDao.findById(postId)
    }

    override fun init() {
        view.showPost(realmResultsWrapper.results)
        view.startVisibleGifAnimation()
    }

    override fun destroy() {
        realmResultsWrapper.close()
    }

    override fun onVisibleToUser(visible: Boolean) {
        if (visible) {
            view.startVisibleGifAnimation()
        } else {
            view.stopAllGifAnimation()
        }
    }

    override fun onScrollChanged() {
        view.startVisibleGifAnimation()
    }
}
