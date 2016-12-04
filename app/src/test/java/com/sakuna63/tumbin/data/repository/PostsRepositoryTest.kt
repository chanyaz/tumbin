package com.sakuna63.tumbin.data.repository

import com.fasterxml.jackson.core.type.TypeReference
import com.sakuna63.tumbin.application.di.module.ApiModule
import com.sakuna63.tumbin.data.api.TumblrService
import com.sakuna63.tumbin.data.dao.DashboardRealmDao
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.data.model.response.PostsResponse
import com.sakuna63.tumbin.data.model.response.TumblrResponse
import com.sakuna63.tumbin.data.util.TestUtils
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import rx.Single

class PostsRepositoryTest {

    lateinit var repository: PostRepository

    @Mock
    lateinit var mockService: TumblrService

    @Mock
    lateinit var mockDashboardRealmDao: DashboardRealmDao

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        repository = PostRepository(mockService, mockDashboardRealmDao)
        `when`(mockService.getDashboard(anyInt(), anyInt(), anyLong(), anyLong(), anyBoolean(), anyBoolean()))
                .thenReturn(Single.fromCallable { RESPONSE })
    }

    @Test
    @Throws(Throwable::class)
    fun fetchDashboard_returnResponseSize() {
        doNothing().`when`(mockDashboardRealmDao).insert(RESPONSE.response.posts)

        val expected = RESPONSE.response.posts.size
        val actual = repository.fetchDashboard(null, null, null, null).toBlocking().value()
        assertThat(actual, `is`(expected))
        verify(mockDashboardRealmDao).insert(RESPONSE.response.posts)
    }

    companion object {
        private val RESPONSE = TestUtils.readJsonAssets(
                ApiModule().objectMapper(), object : TypeReference<TumblrResponse<PostsResponse>>() {
        }, "get_user_dashboard.json")
    }
}
