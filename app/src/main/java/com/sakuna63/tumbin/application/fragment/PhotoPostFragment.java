package com.sakuna63.tumbin.application.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.sakuna63.tumbin.application.contract.PostContract;
import com.sakuna63.tumbin.application.di.component.DaggerPostComponent;
import com.sakuna63.tumbin.application.di.component.PostComponent;
import com.sakuna63.tumbin.application.di.module.PostPresenterModule;
import com.sakuna63.tumbin.application.widget.GifControlImageView;
import com.sakuna63.tumbin.data.model.Post;
import com.sakuna63.tumbin.databinding.FragmentPhotoPostBinding;

import javax.inject.Inject;

@FragmentWithArgs
public class PhotoPostFragment extends BaseFragment
        implements PostContract.View, NestedScrollView.OnScrollChangeListener {
    public static final String TAG = PostsFragment.class.getSimpleName();

    @Arg
    long postId;

    @Inject
    PostContract.Presenter presenter;

    private FragmentPhotoPostBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPhotoPostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.scrollView.setOnScrollChangeListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initInjector();
        presenter.init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void showPost(@NonNull Post post) {
        binding.setPost(post);
        ViewTreeObserver viewTreeObserver = binding.containerPhotos.getViewTreeObserver();
        viewTreeObserver.addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                if (getUserVisibleHint()) {
                    startAnimationGif();
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        boolean isViewNotCreated = binding == null;
        if (isViewNotCreated) {
            return;
        }

        if (isVisibleToUser) {
            startAnimationGif();
        } else {
            stopAnimationGif();
        }
    }

    @Override
    public void startAnimationGif() {
        LinearLayout container = binding.containerPhotos;
        Rect screenBounds = new Rect();
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            binding.scrollView.getHitRect(screenBounds);
            if (child instanceof GifControlImageView && child.getLocalVisibleRect(screenBounds)) {
                ((GifControlImageView) child).setRunnable(true);
            }
        }
    }

    @Override
    public void stopAnimationGif() {
        LinearLayout container = binding.containerPhotos;
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child instanceof GifControlImageView) {
                ((GifControlImageView) child).setRunnable(false);
            }
        }
    }

    @Override
    public void setPresenter(PostContract.Presenter presenter) {
        // presenter is provided by component
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        LinearLayout container = binding.containerPhotos;
        Rect screenBounds = new Rect();
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            v.getHitRect(screenBounds);
            if (!(child instanceof GifControlImageView)) {
                continue;
            }

            if (child.getLocalVisibleRect(screenBounds)) {
                ((GifControlImageView) child).setRunnable(true);
            } else {
                ((GifControlImageView) child).setRunnable(false);
            }
        }
    }

    private void initInjector() {
        PostComponent component = DaggerPostComponent.builder()
                .activityComponent(getActivityComponent())
                .fragmentModule(getFragmentModule())
                .postPresenterModule(new PostPresenterModule(this, postId))
                .build();
        component.inject(this);
    }
}
