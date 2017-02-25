package com.sakuna63.tumbin.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sakuna63.tumbin.application.contract.TextPostContract
import com.sakuna63.tumbin.application.contract.presenter.TextPostPresenter
import com.sakuna63.tumbin.application.misc.Arg
import com.sakuna63.tumbin.data.dao.DashboardRealmDaoImpl
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.databinding.FragmentTextPostBinding
import com.sakuna63.tumbin.extension.put

class TextPostFragment : BaseFragment(), TextPostContract.View {

    val postId: Long by Arg()

    lateinit private var presenter: TextPostContract.Presenter // init on onCreate
    lateinit private var binding: FragmentTextPostBinding // init on onCreateView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TextPostPresenter(postId, this,
                DashboardRealmDaoImpl(activityComponent.realmConfiguration()))
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTextPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.init()
    }

    override fun showPost(post: Post) {
        binding.post = post
    }

    override fun setPresenter(presenter: TextPostContract.Presenter) {
        // no-op
    }

    companion object {
        val TAG = PhotoPostFragment::class.java.simpleName!!

        fun newInstance(postId: Long) = TextPostFragment().apply {
            arguments.put(TextPostFragment::postId, postId)
        }
    }
}
