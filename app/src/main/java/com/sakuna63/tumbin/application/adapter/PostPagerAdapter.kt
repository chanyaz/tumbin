package com.sakuna63.tumbin.application.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.ViewGroup
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.fragment.DashboardPostFragmentBuilder
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.fillUntil

class PostPagerAdapter(fm: FragmentManager, private val context: Context,
                       private val posts: List<Post>) : FragmentPagerAdapter(fm) {
    private val fragments: MutableList<Fragment?> = mutableListOf()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        if (!needVisibilityPropagation(container, position)) {
            return fragment
        }

        fragments.fillUntil(position)
        fragments[position] = fragment

        val viewPager = container as ViewPager
        if (position == viewPager.currentItem + 1) {
            setVisibility(fragment, true)
        }

        return fragment
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        if (!needVisibilityPropagation(container, position)) {
            return
        }

        if (position + 1 < fragments.size) {
            setVisibility(fragments[position + 1]!!, true)
        }
        if (position + 2 < fragments.size) {
            setVisibility(fragments[position + 2]!!, false)
        }
    }

    override fun getItem(position: Int): Fragment {
        val item = posts[position]
        return DashboardPostFragmentBuilder(item.id, item.type).build()
    }

    override fun getCount(): Int = posts.size

    override fun getPageWidth(position: Int): Float =
            1.0f / context.resources.getInteger(R.integer.num_post_pages_same_time)

    private fun needVisibilityPropagation(container: ViewGroup, position: Int): Boolean =
            container is ViewPager && getPageWidth(position) != 1.0f

    private fun setVisibility(fragment: Fragment, visible: Boolean) {
        fragment.setMenuVisibility(visible)
        fragment.userVisibleHint = visible
    }
}
