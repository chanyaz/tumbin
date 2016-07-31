package com.sakuna63.tumblrin.data.net;

import android.support.annotation.IntRange;

import com.sakuna63.tumblrin.data.model.response.PostsResponse;
import com.sakuna63.tumblrin.data.model.response.TumblrResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Single;

public interface TumblrService {

    @GET("/v2/user/dashboard")
    Single<TumblrResponse<PostsResponse>> getDashboard(
            @Query("limit") @IntRange(from = 1, to = 20) Integer limit,
            @Query("offset") @IntRange(from = 0) Integer offset,
//            @Query("type") String type, path???
            @Query("since_id") Long sinceId,
            @Query("reblog_info") Boolean reblogInfo,
            @Query("notes_info") Boolean notesInfo
    );

    @GET("/v2/blog/{blog-identifier}")
    Single<PostsResponse> getPosts(
            @Path("blog-identifier") String blogIdentifier,
            @Query("id") Long id,
            @Query("tag") String tag,
            @Query("limit") @IntRange(from = 1, to = 20) Integer limit,
            @Query("offset") @IntRange(from = 0) Integer offset,
            @Query("reblog_info") Boolean reblogInfo,
            @Query("notes_info") Boolean notesInfo,
            @Query("filter") String filter
    );
}
