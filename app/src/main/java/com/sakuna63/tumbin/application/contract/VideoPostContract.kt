package com.sakuna63.tumbin.application.contract

import com.sakuna63.tumbin.data.model.Post

class VideoPostContract {
    interface View : BaseView<Presenter> {
        fun showPost(post: Post)

        fun playVideo()

        fun pauseVideo()

        fun setVideoVolume(enable: Boolean)
    }

    interface Presenter : BasePresenter {
        fun onVisibleToUser(visible: Boolean)

        fun onVolumeToggleClick()
    }
}
