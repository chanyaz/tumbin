package com.sakuna63.tumbin.application.contract.presenter

import com.sakuna63.tumbin.application.contract.VideoPostContract
import com.sakuna63.tumbin.data.dao.DashboardRealmDao
import com.sakuna63.tumbin.data.dao.RealmResultsWrapper
import com.sakuna63.tumbin.data.model.Post

class VideoPostPresenter(
        private val postId: Long,
        private val view: VideoPostContract.View,
        private val dashboardRealmDao: DashboardRealmDao
) : VideoPostContract.Presenter {

    private var volumeEnabled: Boolean = false

    private val realmResultsWrapper: RealmResultsWrapper<Post> by lazy {
        dashboardRealmDao.findById(postId)
    }

    override fun init() {
        view.showPost(realmResultsWrapper.results)
        view.playVideo()
        view.setVideoVolume(volumeEnabled)
    }

    override fun destroy() {
        realmResultsWrapper.close()
    }

    override fun onVisibleToUser(visible: Boolean) {
        if (visible) {
            view.playVideo()
        } else {
            view.pauseVideo()
        }
    }

    override fun onVolumeToggleClick() {
        volumeEnabled = !volumeEnabled
        view.setVideoVolume(volumeEnabled)
    }
}
