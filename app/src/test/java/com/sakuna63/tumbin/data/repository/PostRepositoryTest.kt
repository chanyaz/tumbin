package com.sakuna63.tumbin.data.repository

import com.fasterxml.jackson.core.type.TypeReference
import com.sakuna63.tumbin.application.di.module.ApiModule
import com.sakuna63.tumbin.data.api.TumblrService
import com.sakuna63.tumbin.data.dao.DashboardRealmDao
import com.sakuna63.tumbin.data.model.response.PostsResponse
import com.sakuna63.tumbin.data.model.response.TumblrResponse
import com.sakuna63.tumbin.data.util.TestUtils

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import java.util.concurrent.Callable

import rx.Single
import rx.observers.TestSubscriber
import rx.schedulers.TestScheduler
import rx.subjects.TestSubject

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.mockito.Matchers.anyBoolean
import org.mockito.Matchers.anyInt
import org.mockito.Matchers.anyLong
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class PostRepositoryTest {

//    internal var repository: PostsRepository
//
//    @Mock
//    internal var mockService: TumblrService? = null
//
//    @Mock
//    internal var mockDashboardRealmDao: DashboardRealmDao? = null
//
//    @Before
//    @Throws(Exception::class)
//    fun setUp() {
//        MockitoAnnotations.initMocks(this)
//
//        repository = PostsRepository(mockService!!, mockDashboardRealmDao!!)
//
//        `when`(mockService!!.getDashboard(anyInt(), anyInt(), anyLong(), anyLong(), anyBoolean(), anyBoolean())).thenReturn(Single.fromCallable { RESPONSE })
//    }
//
//    @Test
//    @Throws(Throwable::class)
//    fun fetchDashboard() {
//        val expectedResponseSize = RESPONSE.response.posts.size
//
//        val testScheduler = TestScheduler()
//        val testSubject = TestSubject.create<TumblrResponse<PostsResponse>>(testScheduler)
//
//        val testSubscriber = TestSubscriber<Int>()
//        run {
//            val single = repository.fetchDashboard(null, null, null, null)
//            single.subscribe(testSubscriber)
//
//            testSubject.onNext(null)
//            testSubject.onCompleted()
//            testScheduler.triggerActions()
//        }
//
//        verify<DashboardRealmDao>(mockDashboardRealmDao).insert(RESPONSE.response.posts)
//
//        val actualResponseSize = testSubscriber.onNextEvents[0]
//        assertThat(actualResponseSize, `is`(expectedResponseSize))
//    }
//
//    companion object {
//        private val RESPONSE = TestUtils.readJsonAssets(
//                ApiModule.objectMapper(), object : TypeReference<TumblrResponse<PostsResponse>>() {
//        }, "get_user_dashboard.json")
//    }
}
