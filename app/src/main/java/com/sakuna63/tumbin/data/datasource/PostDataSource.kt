package com.sakuna63.tumbin.data.datasource

import android.support.annotation.IntRange

import rx.Completable
import rx.Single

interface PostDataSource {
    fun fetchDashboard(@IntRange(from = 1, to = 20) limit: Int?,
                       @IntRange(from = 0) offset: Int?,
                       sinceId: Long?,
                       beforeId: Long?): Single<Int>

    fun deleteAllDashboardCache(): Completable
}
