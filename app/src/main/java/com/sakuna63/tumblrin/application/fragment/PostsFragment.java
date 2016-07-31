package com.sakuna63.tumblrin.application.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewStubProxy;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sakuna63.tumblrin.R;
import com.sakuna63.tumblrin.application.adapter.PostAdapter;
import com.sakuna63.tumblrin.application.contract.PostsContract;
import com.sakuna63.tumblrin.application.widget.listener.OnScrollBottomListener;
import com.sakuna63.tumblrin.data.model.Post;
import com.sakuna63.tumblrin.databinding.FragmentPostsBinding;
import com.tumblr.bookends.Bookends;

import io.realm.RealmResults;

public class PostsFragment extends BaseFragment
        implements PostsContract.View, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = PostsFragment.class.getSimpleName();

    private PostsContract.Presenter presenter;
    private FragmentPostsBinding binding;

    public static PostsFragment newInstance() {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

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

//        binding.setPresenter(presenter);
        presenter.init();

        binding.swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void setPresenter(PostsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPosts(RealmResults<Post> posts) {
        binding.swipeRefresh.setVisibility(View.VISIBLE);

        boolean isInitialized = binding.recycler.getAdapter() != null;
        if (isInitialized) {
            @SuppressWarnings("unchecked")
            Bookends<PostAdapter> bookends = (Bookends<PostAdapter>) binding.recycler.getAdapter();
            PostAdapter postAdapter = bookends.getWrappedAdapter();
            postAdapter.swapItems(posts);
            bookends.notifyDataSetChanged();
            return;
        }

        initList(posts);
    }

    private void initList(RealmResults<Post> posts) {
        // TODO: 2016/07/31 out source to resource
        final int columns = 2;
        final PostAdapter adapter = new PostAdapter(columns, posts);
        final Bookends<PostAdapter> bookends = new Bookends<>(adapter);

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

        // init others
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
    public void showError(String message) {
        if (binding.recycler.getAdapter() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            return;
        }

        ViewStubProxy stubNetworkError = binding.stubNetworkError;
        if (!stubNetworkError.isInflated()) {
            View view = stubNetworkError.getViewStub().inflate();
            view.findViewById(R.id.button_reload).setOnClickListener(this);
        }

        stubNetworkError.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        ViewStubProxy stubNetworkError = binding.stubNetworkError;
        if (stubNetworkError.isInflated()) {
            stubNetworkError.getRoot().setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmpty() {
        ViewStubProxy stubEmptyPost = binding.stubEmptyPost;
        if (!stubEmptyPost.isInflated()) {
            View view = stubEmptyPost.getViewStub().inflate();
            view.findViewById(R.id.button_reload).setOnClickListener(this);
        }

        stubEmptyPost.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        ViewStubProxy stubEmptyPost = binding.stubEmptyPost;
        if (stubEmptyPost.isInflated()) {
            stubEmptyPost.getRoot().setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {
        if (binding.swipeRefresh.isRefreshing()) {
            // refresh circle is displayed by SwipeRefreshLayout
        } else if (binding.recycler.getAdapter() != null && binding.recycler.getAdapter().getItemCount() > 0) {
            // refresh progress circle is displayed in bottom of list by Bookends
            Bookends bookends = (Bookends) binding.recycler.getAdapter();
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
        } else if (binding.recycler.getAdapter() != null && binding.recycler.getAdapter().getItemCount() > 0) {
            Bookends bookends = (Bookends) binding.recycler.getAdapter();
            View footer = bookends.getFooter(0);
            footer.setVisibility(View.GONE);
        } else {
            binding.progressbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPostDetail(Post post) {

    }

    @Override
    public void onClick(View view) {
        presenter.onReloadClick();
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }
}
