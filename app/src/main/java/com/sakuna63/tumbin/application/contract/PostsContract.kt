package com.sakuna63.tumbin.application.contract

import com.sakuna63.tumbin.data.model.Post

interface PostsContract {
    interface View : BaseView<Presenter> {
        fun showPosts(posts: List<Post>)

        fun hidePosts()

        fun showError(message: String)

        fun hideError()

        fun showEmpty()

        fun hideEmpty()

        fun showLoading()

        fun hideLoading()

        fun showPostDetail(post: Post)

        fun scrollTo(postId: Long, offsetPx: Int)

        val scrollY: Int
    }

    interface Presenter : BasePresenter {
        fun onPostClick(post: Post)

        fun onReloadClick()

        fun onRefresh()

        fun onScrollBottom()
    }
}
