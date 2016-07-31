package com.sakuna63.tumblrin.data.datasource;

import android.support.annotation.IntRange;

import com.sakuna63.tumblrin.data.model.Post;

import java.util.List;

import rx.Single;

public interface PostDataSource {
    Single<List<Post>> getDashboard(@IntRange(from = 1, to = 20) Integer limit, @IntRange(from = 0) Integer offset, Long sinceId);
}
