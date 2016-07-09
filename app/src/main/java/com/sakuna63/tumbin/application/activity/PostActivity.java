package com.sakuna63.tumbin.application.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.sakuna63.tumbin.R;
import com.sakuna63.tumbin.application.adapter.PostPagerAdapter;
import com.sakuna63.tumbin.application.di.component.ActivityComponent;
import com.sakuna63.tumbin.application.di.component.DaggerActivityComponent;
import com.sakuna63.tumbin.data.dao.DashboardRealmDao;
import com.sakuna63.tumbin.data.dao.RealmResultsWrapper;
import com.sakuna63.tumbin.data.model.Post;

import javax.inject.Inject;

import io.realm.RealmResults;

public class PostActivity extends BaseActivity {

    @InjectExtra
    long postId;

    @Inject
    DashboardRealmDao dashboardRealmDao;

    private ActivityComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        initInjector();
        Dart.inject(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        RealmResultsWrapper<RealmResults<Post>> postsWrapper = dashboardRealmDao.findByType(Post.PostType.PHOTO);
        RealmResults<Post> posts = postsWrapper.getResults();
        PostPagerAdapter adapter =
                new PostPagerAdapter(getSupportFragmentManager(), this, posts);
        // TODO: 2016/08/17 ページャーが末尾まで行ったら追加読み込み
        viewPager.setAdapter(adapter);

        int currentItem = findCurrentItemIndex(posts);
        viewPager.setCurrentItem(currentItem);
    }

    private int findCurrentItemIndex(@NonNull RealmResults<Post> posts) {
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).id == postId) {
                return i;
            }
        }
        // if not found, use the first post
        return 0;
    }

    @Override
    public ActivityComponent getActivityComponent() {
        return component;
    }

    private void initInjector() {
        //noinspection deprecation
        component = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }
}
