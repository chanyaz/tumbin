package com.sakuna63.tumbin.application.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sakuna63.tumbin.application.contract.PhotoPostContract
import com.sakuna63.tumbin.application.contract.presenter.PhotoPostPresenter
import com.sakuna63.tumbin.application.misc.Arg
import com.sakuna63.tumbin.application.widget.GifControlImageView
import com.sakuna63.tumbin.data.dao.DashboardRealmDaoImpl
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.databinding.FragmentPhotoPostBinding
import com.sakuna63.tumbin.extension.children
import com.sakuna63.tumbin.extension.put

class PhotoPostFragment : BaseFragment(),
        PhotoPostContract.View, NestedScrollView.OnScrollChangeListener {

    val postId: Long by Arg()

    lateinit private var presenter: PhotoPostContract.Presenter // init on onCreate
    lateinit private var binding: FragmentPhotoPostBinding // init on onCreateView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PhotoPostPresenter(postId, this,
                DashboardRealmDaoImpl(activityComponent.realmConfiguration()))
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPhotoPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scrollView.setOnScrollChangeListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.init()
    }

    override fun showPost(post: Post) {
        binding.post = post
        binding.containerPhotos.viewTreeObserver.addOnDrawListener {
            if (userVisibleHint) startVisibleGifAnimation()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        view ?: return
        presenter.onVisibleToUser(isVisibleToUser)
    }

    override fun startVisibleGifAnimation() {
        val screenBounds = Rect().let { binding.scrollView.getHitRect(it); it }
        binding.containerPhotos.children().forEach {
            if (it is GifControlImageView && it.getLocalVisibleRect(screenBounds)) {
                it.isRunnable = true
            }
        }
    }

    override fun stopAllGifAnimation() {
        binding.containerPhotos.children().forEach {
            if (it is GifControlImageView) {
                it.isRunnable = false
            }
        }
    }

    override fun setPresenter(presenter: PhotoPostContract.Presenter) {
        // no-op
    }

    override fun onScrollChange(v: NestedScrollView, scrollX: Int, scrollY: Int,
                                oldScrollX: Int, oldScrollY: Int) {
        presenter.onScrollChanged()
    }

    companion object {
        val TAG = PhotoPostFragment::class.java.simpleName!!

        fun newInstance(postId: Long) = PhotoPostFragment().apply {
            arguments.put(PhotoPostFragment::postId, postId)
        }
    }
}
