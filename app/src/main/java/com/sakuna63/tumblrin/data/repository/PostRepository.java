package com.sakuna63.tumblrin.data.repository;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.sakuna63.tumblrin.application.di.scope.ActivityScope;
import com.sakuna63.tumblrin.data.dao.PostDao;
import com.sakuna63.tumblrin.data.datasource.PostDataSource;
import com.sakuna63.tumblrin.data.model.Post;
import com.sakuna63.tumblrin.data.model.response.PostsResponse;
import com.sakuna63.tumblrin.data.model.response.TumblrResponse;
import com.sakuna63.tumblrin.data.net.TumblrService;

import java.util.List;

import javax.inject.Inject;

import rx.Single;
import rx.functions.Func1;

@ActivityScope
public class PostRepository implements PostDataSource {

    @NonNull
    private final TumblrService service;

    @NonNull
    private final PostDao postDao;

    @Inject
    public PostRepository(@NonNull TumblrService service,
                          @NonNull PostDao postDao) {
        this.service = service;
        this.postDao = postDao;
    }

    @Override
    public Single<List<Post>> getDashboard(@IntRange(from = 1, to = 20) Integer limit,
                                           @IntRange(from = 0) Integer offset, Long sinceId) {
        return service.getDashboard(limit, offset, sinceId, false, false)
                .map(new Func1<TumblrResponse<PostsResponse>, List<Post>>() {
                    @Override
                    public List<Post> call(final TumblrResponse<PostsResponse> response) {
                        List<Post> posts = response.getResponse().getPosts();
                        postDao.insert(posts);
                        return posts;
                    }
                });
    }
}
