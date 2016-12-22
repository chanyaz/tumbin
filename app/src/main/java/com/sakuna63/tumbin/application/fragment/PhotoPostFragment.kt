package com.sakuna63.tumbin.application.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.sakuna63.tumbin.application.contract.PostContract
import com.sakuna63.tumbin.application.widget.GifControlImageView
import com.sakuna63.tumbin.extensions.children
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.databinding.FragmentPhotoPostBinding

@FragmentWithArgs
class PhotoPostFragment : PostFragment(), NestedScrollView.OnScrollChangeListener {

    lateinit internal var presenter: PostContract.Presenter

    private var binding: FragmentPhotoPostBinding? = null

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPhotoPostBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.scrollView.setOnScrollChangeListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.init()
    }

    override fun showPost(post: Post) {
        binding!!.post = post
        val viewTreeObserver = binding!!.containerPhotos.viewTreeObserver
        viewTreeObserver.addOnDrawListener {
            if (userVisibleHint) startAnimationGif()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        val isViewNotCreated = binding == null
        if (isViewNotCreated) {
            return
        }

        if (isVisibleToUser) {
            startAnimationGif()
        } else {
            stopAnimationGif()
        }
    }

    fun startAnimationGif() {
        val screenBounds = Rect()
        binding!!.scrollView.getHitRect(screenBounds)
        binding!!.containerPhotos.children().forEach {
            if (it is GifControlImageView && it.getLocalVisibleRect(screenBounds)) {
                it.isRunnable = true
            }
        }
    }

    fun stopAnimationGif() {
        binding!!.containerPhotos.children().forEach {
            if (it is GifControlImageView) {
                it.isRunnable = false
            }
        }
    }

    override fun setPresenter(presenter: PostContract.Presenter) {
        this.presenter = presenter
    }

    override fun onScrollChange(v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        val screenBounds = Rect()
        binding!!.containerPhotos.children().forEach {
            v.getHitRect(screenBounds)
            if (it !is GifControlImageView) {
                return@forEach
            }
            it.isRunnable = it.getLocalVisibleRect(screenBounds)
        }
    }

    companion object {
        val TAG = PhotoPostFragment::class.java.simpleName
    }
}
