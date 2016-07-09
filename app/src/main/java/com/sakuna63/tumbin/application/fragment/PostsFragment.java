package com.sakuna63.tumbin.application.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewStubProxy;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.sakuna63.tumbin.R;
import com.sakuna63.tumbin.application.activity.Henson;
import com.sakuna63.tumbin.application.adapter.PostAdapter;
import com.sakuna63.tumbin.application.contract.PostsContract;
import com.sakuna63.tumbin.application.util.ViewStubProxyUtils;
import com.sakuna63.tumbin.application.widget.listener.OnScrollBottomListener;
import com.sakuna63.tumbin.data.model.Post;
import com.sakuna63.tumbin.databinding.FragmentPostsBinding;
import com.sakuna63.tumbin.databinding.ViewEmptyPostBinding;
import com.sakuna63.tumbin.databinding.ViewNetworkErrorBinding;
import com.tumblr.bookends.Bookends;

import java.util.List;

import io.realm.RealmResults;

@FragmentWithArgs
public class PostsFragment extends BaseFragment
        implements PostsContract.View, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, PostAdapter.Listener {
    public static final String TAG = PostsFragment.class.getSimpleName();

    private PostsContract.Presenter presenter;
    private FragmentPostsBinding binding;
    private Bookends<PostAdapter> bookends;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void setPresenter(PostsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.init();
    }

    @Override
    public void showPosts(@NonNull List<Post> posts) {
        binding.swipeRefresh.setVisibility(View.VISIBLE);

        boolean isInitialized = binding.recycler.getAdapter() != null;
        if (isInitialized) {
            PostAdapter postAdapter = bookends.getWrappedAdapter();
            postAdapter.swapItems(posts);
            bookends.notifyDataSetChanged();
            return;
        }

        initList(posts);
    }

    private void initList(@NonNull List<Post> posts) {
        final int columns = getContext().getResources().getInteger(R.integer.num_post_columns);
        final PostAdapter adapter = new PostAdapter(columns, posts);
        adapter.setListener(this);
        bookends = new Bookends<>(adapter);

        // init layoutManager
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), columns);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                boolean isFooter = bookends.getItemCount() - 1 == position;
                return isFooter ? columns : 1;
            }
        });
        binding.recycler.setLayoutManager(layoutManager);

        // footer must be inflated after setting layout manager
        View footer = LayoutInflater.from(getContext())
                .inflate(R.layout.list_item_loading, binding.recycler, false);
        bookends.addFooter(footer);
        binding.recycler.setAdapter(bookends);

        // init other settings
        binding.recycler.setHasFixedSize(true);
        binding.recycler.addOnScrollListener(new OnScrollBottomListener(layoutManager) {
            @Override
            public void onScrollBottom() {
                presenter.onScrollBottom();
            }
        });
    }

    @Override
    public void hidePosts() {
        binding.swipeRefresh.setVisibility(View.GONE);
    }

    @Override
    public void showError(@Nullable String message) {
        if (binding.recycler.getAdapter() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            return;
        }

        ViewNetworkErrorBinding bindingNetworkError
                = ViewStubProxyUtils.inflate(binding.stubNetworkError);
        bindingNetworkError.setPresenter(presenter);
        bindingNetworkError.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        ViewStubProxy stubNetworkError = binding.stubNetworkError;
        ViewStubProxyUtils.goneRootIfInflated(stubNetworkError);
    }

    @Override
    public void showEmpty() {
        ViewEmptyPostBinding bindingEmptyPost = ViewStubProxyUtils.inflate(binding.stubEmptyPost);
        bindingEmptyPost.setPresenter(presenter);
        bindingEmptyPost.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        ViewStubProxy stubEmptyPost = binding.stubEmptyPost;
        ViewStubProxyUtils.goneRootIfInflated(stubEmptyPost);
    }

    @Override
    public void showLoading() {
        //noinspection StatementWithEmptyBody
        if (binding.swipeRefresh.isRefreshing()) {
            // refresh circle has already displayed by SwipeRefreshLayout
        } else if (arePostsShown()) {
            View footer = bookends.getFooter(0);
            footer.setVisibility(View.VISIBLE);
        } else {
            binding.progressbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (binding.swipeRefresh.isRefreshing()) {
            binding.swipeRefresh.setRefreshing(false);
        } else if (arePostsShown()) {
            View footer = bookends.getFooter(0);
            footer.setVisibility(View.GONE);
        } else {
            binding.progressbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPostDetail(@NonNull Post post) {
        // TODO: abstraction
        Intent intent = Henson.with(getContext())
                .gotoDashboardPostActivity()
                .postId(post.id)
                .build();
        startActivity(intent);
    }

    @Override
    public void scrollTo(long postId, int offsetPx) {
        PostAdapter adapter = bookends.getWrappedAdapter();

        // find post position
        int position = -1;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).id == postId) {
                position = i;
                break;
            }
        }

        // not found
        if (position == -1) {
            return;
        }

        binding.recycler.scrollToPosition(position + bookends.getHeaderCount());
        binding.recycler.scrollBy(0, offsetPx);
    }

    @Override
    public int getScrollY() {
        return binding.recycler.getScrollY();
    }

    @Override
    public void onClick(View view) {
        presenter.onReloadClick();
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }

    @Override
    public void onPostClick(int position) {
        PostAdapter adapter = bookends.getWrappedAdapter();
        presenter.onPostClick(adapter.getItem(position));
    }

    private boolean arePostsShown() {
        return binding.recycler.getAdapter() != null && binding.recycler.getAdapter().getItemCount() > 0;
    }
}
