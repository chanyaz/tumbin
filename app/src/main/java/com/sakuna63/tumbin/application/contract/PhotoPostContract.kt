package com.sakuna63.tumbin.application.contract

import com.sakuna63.tumbin.data.model.Post

interface PhotoPostContract {
    interface View : BaseView<Presenter> {
        fun showPost(post: Post)

        /**
         * NOTE: View may stop invisible gifs's animation at the same time for performance
         */
        fun startVisibleGifAnimation()

        fun stopAllGifAnimation()
    }

    interface Presenter : BasePresenter {
        fun onVisibleToUser(visible: Boolean)

        fun onScrollChanged()
    }
}
