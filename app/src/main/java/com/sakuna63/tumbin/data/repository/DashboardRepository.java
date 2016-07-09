package com.sakuna63.tumbin.data.repository;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sakuna63.tumbin.data.api.TumblrService;
import com.sakuna63.tumbin.data.dao.DashboardRealmDao;
import com.sakuna63.tumbin.data.datasource.DashboardDataSource;
import com.sakuna63.tumbin.data.model.Post;
import com.sakuna63.tumbin.data.model.response.PostsResponse;
import com.sakuna63.tumbin.data.model.response.TumblrResponse;

import java.util.List;

import javax.inject.Inject;

import rx.Completable;
import rx.Single;
import rx.functions.Action0;
import rx.functions.Func1;

public class DashboardRepository implements DashboardDataSource {

    @NonNull
    private final TumblrService service;

    @NonNull
    private final DashboardRealmDao dashboardRealmDao;

    @Inject
    public DashboardRepository(@NonNull TumblrService service,
                               @NonNull DashboardRealmDao dashboardRealmDao) {
        this.service = service;
        this.dashboardRealmDao = dashboardRealmDao;
    }

    @NonNull
    @Override
    public Single<Integer> fetchDashboard(@Nullable @IntRange(from = 1, to = 20) Integer limit,
                                          @Nullable @IntRange(from = 0) Integer offset,
                                          @Nullable Long sinceId,
                                          @Nullable Long beforeId) {

        return service.getDashboard(limit, offset, sinceId, beforeId, true, true)
                .map(new Func1<TumblrResponse<PostsResponse>, Integer>() {
                    @Override
                    public Integer call(final TumblrResponse<PostsResponse> response) {
                        List<Post> posts = response.getResponse().getPosts();
                        dashboardRealmDao.insert(posts);
                        return posts.size();
                    }
                });
    }

    public Completable deleteAllCache() {
        return Completable.fromAction(new Action0() {
            @Override
            public void call() {
                dashboardRealmDao.deleteAll();
            }
        });
    }
}
