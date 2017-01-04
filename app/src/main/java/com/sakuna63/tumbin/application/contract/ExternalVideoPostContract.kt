package com.sakuna63.tumbin.application.contract

import com.sakuna63.tumbin.data.model.Post

interface ExternalVideoPostContract {
    interface View : BaseView<Presenter> {
        fun showPost(post: Post)

        fun openBrowser(url: String)
    }

    interface Presenter : BasePresenter {
        fun onThumbnailClick()
    }
}
