package com.sakuna63.tumbin.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.contract.PostContract
import com.sakuna63.tumbin.application.contract.presenter.DashboardPostPresenter
import com.sakuna63.tumbin.application.di.component.DaggerDashboardPostComponent
import com.sakuna63.tumbin.application.di.module.PostPresenterModule
import com.sakuna63.tumbin.application.util.FragmentUtils
import com.sakuna63.tumbin.data.model.Post
import javax.inject.Inject

@FragmentWithArgs
class DashboardPostFragment : BaseFragment() {

    @Arg
    var postId: Long = 0

    @Arg
    @Post.PostType
    lateinit internal var postType: String

    @Inject
    lateinit internal var presenter: DashboardPostPresenter

    private var isCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCreated = true
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_dashboard_post, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var fragment = findFragment()
        if (fragment == null) {
            fragment = createPostFragmentByType(postType)
            FragmentUtils.addFragment(childFragmentManager,
                    fragment, R.id.container_fragment, PostFragment.TAG)
        }

        initInjector(fragment, postId)
    }

    private fun findFragment() =
            childFragmentManager.findFragmentByTag(PostFragment.TAG) as PostFragment?

    override fun onDestroyView() {
        super.onDestroyView()
        // call destroy() here, because presenter is injected on onViewCreated
        presenter.destroy()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        // Fragment will crash if we call getChildFragmentManager before fragment created and in initialization process.
        // So we ignore calling it before fragment is created.
        // This may be not a valid solution...:(
        if (!isCreated) return
        val fragment = findFragment()
        fragment?.userVisibleHint = isVisibleToUser
    }

    private fun initInjector(view: PostContract.View, postId: Long) {
        val component = DaggerDashboardPostComponent.builder()
                .activityComponent(activityComponent)
                .fragmentModule(fragmentModule)
                .postPresenterModule(PostPresenterModule(view, postId))
                .build()
        component.inject(this)
    }

    private fun createPostFragmentByType(@Post.PostType type: String): PostFragment {
        return when (type) {
            Post.TYPE_PHOTO -> PhotoPostFragmentBuilder().build()
            Post.TYPE_VIDEO -> VideoPostFragmentBuilder().build()
            Post.TYPE_TEXT -> TextPostFragmentBuilder().build()
            else -> throw IllegalArgumentException("Doesn't support yet type: $type")
        }
    }

    companion object {
        val TAG = PostsFragment::class.java.simpleName
    }
}
