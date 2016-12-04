package com.sakuna63.tumbin.application.contract

import com.sakuna63.tumbin.data.model.Post

interface PostContract {
    interface View : BaseView<Presenter> {
        fun showPost(post: Post)

        fun startAnimationGif()

        fun stopAnimationGif()
    }

    interface Presenter : BasePresenter
}
