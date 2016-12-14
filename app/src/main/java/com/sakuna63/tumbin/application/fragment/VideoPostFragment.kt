package com.sakuna63.tumbin.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.sakuna63.tumbin.application.contract.PostContract
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.databinding.FragmentVideoPostBinding


@FragmentWithArgs
class VideoPostFragment : PostFragment() {

    lateinit private var presenter: PostContract.Presenter // init on setPresenter
    lateinit private var binding: FragmentVideoPostBinding // init on onCreateView

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
        binding.exoPlayerView.viewTreeObserver.addOnDrawListener {
            if (userVisibleHint) playVideo()
        }
        binding.buttonVolumeToggle.setOnClickListener {
            // First, we use activation state. But somehow...when we change view's activation state,
            // drawable state is not changed. So we use selection state although it's ugly.
            // TODO: In two columns pager, second column (not truly primary) page's buttons can't be selected...
            val volume = if (it.isSelected) 0.0f else 0.5f
            binding.exoPlayerView.player.volume = volume
            it.isSelected = !it.isSelected
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        view ?: return

        if (isVisibleToUser) {
            playVideo()
        } else {
            pauseVideo()
        }
    }

    private fun playVideo() {
        binding.exoPlayerView.player?.playWhenReady = true
    }

    private fun pauseVideo() {
        binding.exoPlayerView.player?.playWhenReady = false
    }

    // TODO: save state
    override fun onDestroyView() {
        super.onDestroyView()
        val player = binding.exoPlayerView.player
        if (player != null) {
            binding.exoPlayerView.player = null
            player.playWhenReady = false
            player.release()
        }
    }

    override fun setPresenter(presenter: PostContract.Presenter) {
        this.presenter = presenter
    }

    companion object {
        val TAG = VideoPostFragment::class.java.simpleName
    }
}
