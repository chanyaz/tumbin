package com.sakuna63.tumbin.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sakuna63.tumbin.application.contract.VideoPostContract
import com.sakuna63.tumbin.application.contract.presenter.VideoPostPresenter
import com.sakuna63.tumbin.application.misc.Arg
import com.sakuna63.tumbin.data.dao.DashboardRealmDaoImpl
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.databinding.FragmentVideoPostBinding
import com.sakuna63.tumbin.extension.put


class VideoPostFragment : BaseFragment(), VideoPostContract.View {

    val postId: Long by Arg()

    lateinit private var presenter: VideoPostContract.Presenter // init on onCreate
    lateinit private var binding: FragmentVideoPostBinding // init on onCreateView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = VideoPostPresenter(postId, this,
                DashboardRealmDaoImpl(activityComponent.realmConfiguration()))
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentVideoPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.init()
    }

    override fun showPost(post: Post) {
        binding.post = post
        binding.presenter = presenter
        binding.exoPlayerView.viewTreeObserver.addOnDrawListener {
            if (userVisibleHint) playVideo()
        }
        binding.executePendingBindings()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        view ?: return
        presenter.onVisibleToUser(isVisibleToUser)
    }

    override fun playVideo() {
        binding.exoPlayerView.player?.playWhenReady = true
    }

    override fun pauseVideo() {
        binding.exoPlayerView.player?.playWhenReady = false
    }

    override fun setVideoVolume(enable: Boolean) {
        val volume = if (!enable) 0.0f else 0.5f
        binding.exoPlayerView.player?.volume = volume

        // First, we use activation state. But somehow...when we change view's activation state,
        // drawable state is not changed. So we use selection state although it's ugly.
        // TODO: In two columns pager, second column (not truly primary) page's buttons can't be selected...
        binding.buttonVolumeToggle.isSelected = enable
    }

    override fun onDestroyView() {
        super.onDestroyView()
        releaseExoPlayer()
    }

    private fun releaseExoPlayer() {
        val player = binding.exoPlayerView.player
        if (player != null) {
            binding.exoPlayerView.player = null
            player.playWhenReady = false
            player.release()
        }
    }

    override fun setPresenter(presenter: VideoPostContract.Presenter) {
        // no-op
    }

    companion object {
        val TAG = VideoPostFragment::class.java.simpleName!!

        fun newInstance(postId: Long) = VideoPostFragment().apply {
            arguments.put(VideoPostFragment::postId, postId)
        }
    }
}
