package com.sakuna63.tumbin.application.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.adapter.PostPagerAdapter
import com.sakuna63.tumbin.application.di.component.ActivityComponent
import com.sakuna63.tumbin.application.di.component.DaggerActivityComponent
import com.sakuna63.tumbin.extensions.bindView
import com.sakuna63.tumbin.data.dao.DashboardRealmDao
import com.sakuna63.tumbin.data.model.Post
import io.realm.RealmResults
import javax.inject.Inject

class DashboardPostActivity : BaseActivity() {

    companion object {
        private val EXTRA_POST_ID = "${DashboardPostActivity::class.java.simpleName}.EXTRA_POST_ID"

        fun intent(context: Context, postId: Long): Intent {
            val intent = Intent(context, DashboardPostActivity::class.java)
            intent.putExtra(EXTRA_POST_ID, postId)
            return intent
        }
    }

    @Inject
    lateinit var dashboardRealmDao: DashboardRealmDao

    private val viewPager: ViewPager by bindView(R.id.viewpager)

    override val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(activityModule)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        activityComponent.inject(this)

        val postsWrapper = dashboardRealmDao.findByTypes(Post.TYPE_PHOTO, Post.TYPE_TEXT)
        val posts = postsWrapper.results
        viewPager.adapter = PostPagerAdapter(supportFragmentManager, this, posts)

        val postId = intent.getLongExtra(EXTRA_POST_ID, -1)
        viewPager.currentItem = findCurrentItemIndex(posts, postId)

        // TODO: 2016/08/17 ページャーが末尾まで行ったら追加読み込み
    }

    private fun findCurrentItemIndex(posts: RealmResults<Post>, id: Long): Int {
        val currentPost = posts.find { it.id == id }
        // if not found, use the first post
        return if (currentPost == null) 0 else posts.indexOf(currentPost)
    }
}
