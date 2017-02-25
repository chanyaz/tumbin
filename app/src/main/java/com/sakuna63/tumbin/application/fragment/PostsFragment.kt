package com.sakuna63.tumbin.application.fragment

import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.activity.DashboardPostActivity
import com.sakuna63.tumbin.application.adapter.PostAdapter
import com.sakuna63.tumbin.application.contract.PostsContract
import com.sakuna63.tumbin.application.widget.listener.OnScrollBottomListener
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.databinding.FragmentPostsBinding
import com.sakuna63.tumbin.databinding.ViewEmptyPostBinding
import com.sakuna63.tumbin.databinding.ViewNetworkErrorBinding
import com.sakuna63.tumbin.extension.goneRootIfInflated
import com.sakuna63.tumbin.extension.inflateIfNeeded
import com.tumblr.bookends.Bookends

class PostsFragment : BaseFragment(), PostsContract.View, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, PostAdapter.Listener {

    lateinit private var presenter: PostsContract.Presenter
    lateinit private var binding: FragmentPostsBinding
    private var bookends: Bookends<PostAdapter>? = null

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentPostsBinding>(inflater, R.layout.fragment_posts, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefresh.setOnRefreshListener(this)
    }

    override fun setPresenter(presenter: PostsContract.Presenter) {
        this.presenter = presenter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.init()
    }

    override fun showPosts(posts: List<Post>) {
        binding.swipeRefresh.visibility = View.VISIBLE

        val isInitialized = binding.recycler.adapter != null
        if (isInitialized) {
            val postAdapter = bookends!!.wrappedAdapter
            postAdapter.swapItems(posts)
            bookends!!.notifyDataSetChanged()
            return
        }

        initList(posts)
    }

    private fun initList(posts: List<Post>) {
        val columns = context.resources.getInteger(R.integer.num_post_columns)
        val adapter = PostAdapter(columns, posts)
        adapter.listener = this
        bookends = Bookends(adapter)

        // init layoutManager
        val layoutManager = GridLayoutManager(context, columns)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val isFooter = bookends!!.itemCount - 1 == position
                return if (isFooter) columns else 1
            }
        }
        binding.recycler.layoutManager = layoutManager

        // footer must be inflated after setting layout manager
        val footer = LayoutInflater.from(context).
                inflate(R.layout.list_item_loading, binding.recycler, false)
        bookends!!.addFooter(footer)
        binding.recycler.adapter = bookends

        // init other settings
        binding.recycler.setHasFixedSize(true)
        binding.recycler.addOnScrollListener(object : OnScrollBottomListener(layoutManager) {
            override fun onScrollBottom() {
                presenter.onScrollBottom()
            }
        })
    }

    override fun hidePosts() {
        binding.swipeRefresh.visibility = View.GONE
    }

    override fun showError(message: String) {
        if (binding.recycler.adapter != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            return
        }

        binding.stubNetworkError.inflateIfNeeded<ViewNetworkErrorBinding>().apply {
            presenter = this@PostsFragment.presenter
            root.visibility = View.VISIBLE
        }
    }

    override fun hideError() = binding.stubNetworkError.goneRootIfInflated()

    override fun showEmpty() {
        binding.stubEmptyPost.inflateIfNeeded<ViewEmptyPostBinding>().apply {
            presenter = this@PostsFragment.presenter
            root.visibility = View.VISIBLE
        }
    }

    override fun hideEmpty() = binding.stubEmptyPost.goneRootIfInflated()

    override fun showLoading() {
        //noinspection StatementWithEmptyBody
        when {
            binding.swipeRefresh.isRefreshing -> {
                // refresh circle has already displayed by SwipeRefreshLayout
            }
            arePostsShown() -> {
                val footer = bookends!!.getFooter(0)
                footer.visibility = View.VISIBLE
            }
            else -> {
                binding.progressbar.visibility = View.VISIBLE
            }
        }
    }

    override fun hideLoading() {
        when {
            binding.swipeRefresh.isRefreshing -> {
                binding.swipeRefresh.isRefreshing = false
            }
            arePostsShown() -> {
                val footer = bookends!!.getFooter(0)
                footer.visibility = View.GONE
            }
            else -> {
                binding.progressbar.visibility = View.GONE
            }
        }
    }

    override fun showPostDetail(post: Post) {
        // TODO: abstraction
        val intent = DashboardPostActivity.intent(context, post.id)
        startActivity(intent)
    }

    override fun openBrowser(permalinkUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(permalinkUrl))
        startActivity(intent)
    }

    override fun scrollTo(postId: Long, offsetPx: Int) {
        val adapter = bookends!!.wrappedAdapter

        // find post position
        val position = (0..adapter.itemCount - 1).map { adapter.getItem(it).id }.indexOf(postId)

        // not found
        if (position == -1) {
            return
        }

        binding.recycler.scrollToPosition(position + bookends!!.headerCount)
        binding.recycler.scrollBy(0, offsetPx)
    }

    override val scrollY: Int
        get() = binding.recycler.scrollY

    override fun onClick(view: View) {
        presenter.onReloadClick()
    }

    override fun onRefresh() {
        presenter.onRefresh()
    }

    override fun onPostClick(position: Int) {
        val adapter = bookends!!.wrappedAdapter
        presenter.onPostClick(adapter.getItem(position))
    }

    private fun arePostsShown(): Boolean =
            binding.recycler.adapter != null && binding.recycler.adapter.itemCount > 0

    companion object {
        val TAG = PostsFragment::class.java.simpleName!!

        fun newInstance() = PostsFragment()
    }
}
