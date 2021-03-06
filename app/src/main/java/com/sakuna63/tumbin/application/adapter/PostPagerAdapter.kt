package com.sakuna63.tumbin.application.adapter

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.fragment.ExternalVideoPostFragment
import com.sakuna63.tumbin.application.fragment.PhotoPostFragment
import com.sakuna63.tumbin.application.fragment.TextPostFragment
import com.sakuna63.tumbin.application.fragment.VideoPostFragment
import com.sakuna63.tumbin.data.model.Post

class PostPagerAdapter(private val fm: FragmentManager,
                       private val context: Context,
                       private val posts: List<Post>) : FragmentPagerAdapter(fm) {

    var containerId: Int? = null

    override fun startUpdate(container: ViewGroup?) {
        super.startUpdate(container)
        containerId = container?.id
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)

        var pos = position
        var pageWidth = getPageWidth(position)
        while (pageWidth < 1.0f) {
            setFragmentVisibility(++pos, true)
            pageWidth += getPageWidth(pos)
        }
        setFragmentVisibility(++pos, false)
    }

    override fun getItem(position: Int): Fragment {
        val item = posts[position]
        return buildFragments(item)
    }

    private fun buildFragments(post: Post): Fragment {
        return when (post.type) {
            Post.TYPE_PHOTO -> PhotoPostFragment.newInstance(post.id)
            Post.TYPE_VIDEO -> {
                if (post.videoUrl == null) {
                    return ExternalVideoPostFragment.newInstance(post.id)
                } else {
                    return VideoPostFragment.newInstance(post.id)
                }
            }
            Post.TYPE_TEXT -> TextPostFragment.newInstance(post.id)
            else -> throw IllegalArgumentException("Doesn't support yet type: ${post.type}")
        }
    }

    override fun getCount(): Int = posts.size

    override fun getPageWidth(position: Int): Float =
            1.0f / context.resources.getInteger(R.integer.num_post_pages_same_time)

    private fun setFragmentVisibility(position: Int, visible: Boolean) {
        val fragment: Fragment? = fm.findFragmentByTag(makeFragmentName(containerId!!, position))
        fragment?.setMenuVisibility(visible)
        fragment?.userVisibleHint = visible
    }

    private fun makeFragmentName(@LayoutRes viewId: Int, position: Int): String {
        return "android:switcher:$viewId:$position"
    }
}
