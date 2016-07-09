package com.sakuna63.tumbin.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import com.sakuna63.tumbin.R;
import com.sakuna63.tumbin.application.fragment.PhotoPostFragmentBuilder;
import com.sakuna63.tumbin.data.model.Post;
import com.sakuna63.tumbin.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class PostPagerAdapter extends FragmentPagerAdapter {

    @NonNull
    private final Context context;
    @NonNull
    private final List<Post> posts;
    @NonNull
    private final List<Fragment> fragments;

    public PostPagerAdapter(@NonNull FragmentManager fm, @NonNull Context context,
                            @NonNull List<Post> posts) {
        super(fm);
        this.context = context;
        this.posts = posts;
        this.fragments = new ArrayList<>(posts.size());

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("PostPagerAdapter", "instantiateItem() called with " + "position = [" + position + "]");
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        if (needVisibilityPropagation(container, position)) {
            return fragment;
        }

        CollectionUtils.fillUntil(fragments, position);
        fragments.set(position, fragment);

        ViewPager viewPager = (ViewPager) container;
        if (position == viewPager.getCurrentItem() + 1) {
            setVisibility(fragment, true);
        }

        return fragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Log.d("PostPagerAdapter", "setPrimaryItem() called with " + "position = [" + position + "]");
        super.setPrimaryItem(container, position, object);
        if (needVisibilityPropagation(container, position)) {
            return;
        }

        if (position + 1 < fragments.size()) {
            setVisibility(fragments.get(position + 1), true);
        }
        if (position + 2 < fragments.size()) {
            setVisibility(fragments.get(position + 2), false);
        }
    }

    @Override
    public Fragment getItem(int position) {
        Post item = posts.get(position);
        // TODO: 2016/08/16 handle other post type
        return new PhotoPostFragmentBuilder(item.id).build();
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public float getPageWidth(int position) {
        return 1.0f / context.getResources().getInteger(R.integer.num_post_pages_same_time);
    }

    private boolean needVisibilityPropagation(ViewGroup container, int position) {
        return !(container instanceof ViewPager) || getPageWidth(position) == 1.0f;
    }

    private void setVisibility(@NonNull Fragment fragment, boolean visible) {
        fragment.setMenuVisibility(visible);
        fragment.setUserVisibleHint(visible);
    }
}
