package com.sakuna63.tumbin.data.api

import android.support.annotation.IntRange
import com.sakuna63.tumbin.data.model.response.PostsResponse
import com.sakuna63.tumbin.data.model.response.TumblrResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Single

open interface TumblrService {

    @GET("/v2/user/dashboard")
    fun getDashboard(
            @Query("limit") @IntRange(from = 1, to = 20) limit: Int?,
            @Query("offset") @IntRange(from = 0) offset: Int?,
            //            @Query("type") String type, path???
            @Query("since_id") sinceId: Long?,
            @Query("before_id") beforeId: Long?,
            @Query("reblog_info") reblogInfo: Boolean?,
            @Query("notes_info") notesInfo: Boolean?): Single<TumblrResponse<PostsResponse>>
}
