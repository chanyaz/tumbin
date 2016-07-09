package com.sakuna63.tumbin.application.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.sakuna63.tumbin.R;
import com.sakuna63.tumbin.application.contract.PostContract;
import com.sakuna63.tumbin.application.contract.presenter.DashboardPostPresenter;
import com.sakuna63.tumbin.application.di.component.DaggerDashboardPostComponent;
import com.sakuna63.tumbin.application.di.component.DashboardPostComponent;
import com.sakuna63.tumbin.application.di.module.PostPresenterModule;
import com.sakuna63.tumbin.application.util.FragmentUtils;
import com.sakuna63.tumbin.data.model.Post;

import javax.inject.Inject;

@FragmentWithArgs
public class DashboardPostFragment extends BaseFragment {
    public static final String TAG = PostsFragment.class.getSimpleName();

    @Arg
    long postId;

    @Arg
    @Post.PostType
    String postType;

    @Inject
    DashboardPostPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_post, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentManager fm = getChildFragmentManager();
        PostFragment fragment = (PostFragment) fm.findFragmentByTag(PostFragment.TAG);
        if (fragment == null) {
            fragment = createPostFragmentByType(postType);
            FragmentUtils.addFragment(fm, fragment, R.id.container_fragment, PostFragment.TAG);
        }

        initInjector(fragment, postId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    private void initInjector(@NonNull PostContract.View view, long postId) {
        DashboardPostComponent component = DaggerDashboardPostComponent.builder()
                .activityComponent(getActivityComponent())
                .fragmentModule(getFragmentModule())
                .postPresenterModule(new PostPresenterModule(view, postId))
                .build();
        component.inject(this);
    }

    @NonNull
    private PostFragment createPostFragmentByType(@Post.PostType String type) {
        // TODO: 2016/08/16 handle other post type
        return new PhotoPostFragmentBuilder().build();
    }
}
