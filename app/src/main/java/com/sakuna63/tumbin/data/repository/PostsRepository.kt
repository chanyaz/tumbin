package com.sakuna63.tumbin.data.repository

import android.support.annotation.IntRange
import com.sakuna63.tumbin.data.api.TumblrService
import com.sakuna63.tumbin.data.dao.DashboardRealmDao
import com.sakuna63.tumbin.data.datasource.PostsDataSource
import rx.Completable
import rx.Single
import javax.inject.Inject

class PostsRepository
@Inject
constructor(private val service: TumblrService,
            private val dashboardRealmDao: DashboardRealmDao) : PostsDataSource {

    override fun fetchDashboard(@IntRange(from = 1, to = 20) limit: Int?,
                                @IntRange(from = 0) offset: Int?,
                                sinceId: Long?,
                                beforeId: Long?): Single<Int> {

        return service.getDashboard(limit, offset, sinceId, beforeId, true, true).map { response ->
            val posts = response.response.posts
            dashboardRealmDao.insert(posts)
            posts.size
        }
    }

    override fun deleteAllDashboardCache(): Completable =
            Completable.fromAction { dashboardRealmDao.deleteAll() }
}
