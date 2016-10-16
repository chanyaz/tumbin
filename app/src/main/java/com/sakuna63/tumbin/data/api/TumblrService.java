package com.sakuna63.tumbin.data.api;

import android.support.annotation.IntRange;

import com.sakuna63.tumbin.data.model.response.BlogResponse;
import com.sakuna63.tumbin.data.model.response.PostsResponse;
import com.sakuna63.tumbin.data.model.response.TumblrResponse;

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
            @Query("before_id") Long beforeId,
            @Query("reblog_info") Boolean reblogInfo,
            @Query("notes_info") Boolean notesInfo
    );

    @GET("/v2/blog/{blog-identifier}")
    Single<TumblrResponse<PostsResponse>> getPosts(
            @Path("blog-identifier") String blogIdentifier,
            @Query("id") Long id,
            @Query("tag") String tag,
            @Query("limit") @IntRange(from = 1, to = 20) Integer limit,
            @Query("offset") @IntRange(from = 0) Integer offset,
            @Query("reblog_info") Boolean reblogInfo,
            @Query("notes_info") Boolean notesInfo,
            @Query("filter") String filter
    );

    @GET("/v2/blog/{blog_identifier}/info")
    Single<TumblrResponse<BlogResponse>> getPosts(
            @Path("blog-identifier") String blogIdentifier,
            @Query("is_full_blog_info") boolean isFullBlogInfo);

}
