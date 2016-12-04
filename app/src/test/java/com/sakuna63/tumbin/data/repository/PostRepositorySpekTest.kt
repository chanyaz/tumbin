package com.sakuna63.tumbin.data.repository

import com.fasterxml.jackson.core.type.TypeReference
import com.sakuna63.tumbin.application.di.module.ApiModule
import com.sakuna63.tumbin.data.api.TumblrService
import com.sakuna63.tumbin.data.dao.DashboardRealmDao
import com.sakuna63.tumbin.data.model.response.PostsResponse
import com.sakuna63.tumbin.data.model.response.TumblrResponse
import com.sakuna63.tumbin.data.util.TestUtils
import org.hamcrest.CoreMatchers.`is`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertThat
import org.mockito.Mockito.*
import rx.Single

class PostRepositorySpekTest : Spek({

    given("a post repository") {

        var mockService = mock(TumblrService::class.java)
        var mockDashboardRealmDao = mock(DashboardRealmDao::class.java)
        var testData = loadTestData()
        var repository = PostRepository(mockService, mockDashboardRealmDao)

        beforeEachTest {
            mockService = mock(TumblrService::class.java)
            mockDashboardRealmDao = mock(DashboardRealmDao::class.java)
            testData = loadTestData()
            repository = PostRepository(mockService, mockDashboardRealmDao)
            `when`(mockService.getDashboard(anyInt(), anyInt(), anyLong(), anyLong(), anyBoolean(), anyBoolean()))
                    .thenReturn(Single.fromCallable { testData })
        }

        it("should return response size") {
            val expected = testData.response.posts.size
            val actual = repository.fetchDashboard(null, null, null, null).toBlocking().value()
            assertThat(actual, `is`(expected))
            verify(mockDashboardRealmDao).insert(testData.response.posts)
        }
    }
})

fun loadTestData() = TestUtils.readJsonAssets(ApiModule().objectMapper(),
        object : TypeReference<TumblrResponse<PostsResponse>>() {
        }, "get_user_dashboard.json")

