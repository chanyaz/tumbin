package com.sakuna63.tumbin.application.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sakuna63.tumbin.R;

public class BlogFragment extends BaseFragment {
    public static final String TAG = BlogFragment.class.getSimpleName();

    // Note: only the system can call this constructor by reflection. 
    public BlogFragment() {
    }

    public static BlogFragment newInstance() {
        BlogFragment fragment = new BlogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blog, container, false);
    }
}