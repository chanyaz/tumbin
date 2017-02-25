package com.sakuna63.tumbin.application.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.contract.PostsContract
import com.sakuna63.tumbin.application.contract.presenter.DashboardPresenter
import com.sakuna63.tumbin.application.di.component.ActivityComponent
import com.sakuna63.tumbin.application.di.component.HomeComponent
import com.sakuna63.tumbin.application.di.module.PostsPresenterModule
import com.sakuna63.tumbin.application.fragment.PostsFragment
import com.sakuna63.tumbin.application.misc.AccountManager
import com.sakuna63.tumbin.extension.addFragment
import com.sakuna63.tumbin.extension.bindView
import javax.inject.Inject

class HomeActivity : BaseActivity() {
    @Inject
    lateinit internal var presenter: DashboardPresenter

    @Inject
    lateinit internal var accountManager: AccountManager

    private val toolBar: Toolbar by bindView(R.id.toolbar)

    lateinit private var component: HomeComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolBar)

        val fm = supportFragmentManager
        var fragment: PostsFragment? = fm.findFragmentByTag(PostsFragment.TAG) as PostsFragment?
        if (fragment == null) {
            fragment = PostsFragment.newInstance()
            addFragment(fragment, R.id.container_fragment, PostsFragment.TAG)
        }

        initInjector(fragment)

        if (!accountManager.isLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override val activityComponent: ActivityComponent
        get() = component

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_reload -> {
                presenter.onReloadClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initInjector(view: PostsContract.View) {
        component = applicationComponent.plus(activityModule, PostsPresenterModule(view))
        component.inject(this)
    }
}
