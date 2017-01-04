package com.sakuna63.tumbin.application.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.sakuna63.tumbin.application.contract.ExternalVideoPostContract
import com.sakuna63.tumbin.application.contract.presenter.ExternalVideoPostPresenter
import com.sakuna63.tumbin.data.dao.DashboardRealmDaoImpl
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.databinding.FragmentExternalVideoPostBinding

@FragmentWithArgs
class ExternalVideoPostFragment : BaseFragment(), ExternalVideoPostContract.View {

    @Arg
    var postId: Long = 0

    lateinit private var presenter: ExternalVideoPostContract.Presenter // init on onCreate
    lateinit private var binding: FragmentExternalVideoPostBinding // init on onCreateView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ExternalVideoPostPresenter(postId, this,
                DashboardRealmDaoImpl(activityComponent.realmConfiguration()))
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentExternalVideoPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.init()
    }

    override fun showPost(post: Post) {
        binding.post = post
        binding.presenter = presenter
    }

    override fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun setPresenter(presenter: ExternalVideoPostContract.Presenter) {
        // no-op
    }

    companion object {
        val TAG = PhotoPostFragment::class.java.simpleName
    }
}
