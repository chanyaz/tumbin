package com.sakuna63.tumbin.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.sakuna63.tumbin.application.contract.PostContract
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.databinding.FragmentTextPostBinding

@FragmentWithArgs
class TextPostFragment : PostFragment() {

    lateinit internal var presenter: PostContract.Presenter

    private var binding: FragmentTextPostBinding? = null

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTextPostBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.init()
    }

    override fun showPost(post: Post) {
        binding!!.post = post
    }

    override fun setPresenter(presenter: PostContract.Presenter) {
        this.presenter = presenter
    }

    companion object {
        val TAG = PhotoPostFragment::class.java.simpleName
    }
}
