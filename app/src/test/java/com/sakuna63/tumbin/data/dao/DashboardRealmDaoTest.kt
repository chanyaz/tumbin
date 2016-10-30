package com.sakuna63.tumbin.data.dao

import com.sakuna63.tumbin.data.model.AltSize
import com.sakuna63.tumbin.data.model.Avatar
import com.sakuna63.tumbin.data.model.Photo
import com.sakuna63.tumbin.data.model.Post
import com.sakuna63.tumbin.data.model.Trail
import com.sakuna63.tumbin.data.model.boxing.RealmString

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.rule.PowerMockRule

import java.util.ArrayList
import java.util.Arrays

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.Sort
import io.realm.internal.RealmCore
import io.realm.log.RealmLog

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.mockito.Matchers.any
import org.mockito.Matchers.anyString
import org.mockito.Mockito.doCallRealMethod
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.powermock.api.mockito.PowerMockito.mockStatic

@PrepareForTest(Realm::class, RealmConfiguration::class, RealmQuery<*>::class, RealmResults<*>::class, RealmCore::class, RealmLog::class)
class DashboardRealmDaoTest {
    @Rule
    var powerMockRule = PowerMockRule()

    internal var dao: DashboardRealmDao

    internal var mockRealm: Realm

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mockStatic(RealmCore::class.java)
        mockStatic(RealmLog::class.java)
        mockStatic(Realm::class.java)
        mockStatic(RealmConfiguration::class.java)
        mockStatic(RealmQuery<*>::class.java)
        mockStatic(RealmResults<*>::class.java)

        dao = DashboardRealmDao(null)

        mockRealm = PowerMockito.mock(Realm::class.java)
        val mockRealmQuery = PowerMockito.mock<RealmQuery<*>>(RealmQuery<*>::class.java!!)
        val mockRealmResults = PowerMockito.mock<RealmResults<*>>(RealmResults<*>::class.java!!)
        PowerMockito.`when`(Realm.getInstance(any<Any>() as RealmConfiguration)).thenReturn(mockRealm)

        `when`(mockRealm.where(Post::class.java)).thenReturn(mockRealmQuery)
        doCallRealMethod().`when`(mockRealm).executeTransaction(Mockito.any(Realm.Transaction::class.java))

        val post1 = Post()
        post1.id = 1
        post1.type = Post.PostType.PHOTO

        val post2 = Post()
        post2.id = 2
        post2.type = Post.PostType.PHOTO

        val post3 = Post()
        post3.id = 3
        post3.type = Post.PostType.TEXT

        val post4 = Post()
        post4.id = 4
        post4.type = Post.PostType.ANSWER


        val posts = Arrays.asList(post1, post2, post3, post4)
        val photoPosts = posts.subList(0, 2)

        // for id matching
        `when`<RealmQuery<Post>>(mockRealmQuery.equalTo("id", 1L)).thenReturn(mockRealmQuery)
        `when`<Post>(mockRealmQuery.findFirst()).thenReturn(post1)

        // for type matching
        `when`<RealmQuery<Post>>(mockRealmQuery.equalTo("type", Post.PostType.PHOTO)).thenReturn(mockRealmQuery)
        `when`<RealmResults<Post>>(mockRealmQuery.findAllSorted(anyString(), Matchers.any<Sort>())).thenReturn(mockRealmResults)
        `when`<Iterator<Post>>(mockRealmResults.iterator()).thenReturn(photoPosts.iterator())
    }

    @Test
    @Throws(Throwable::class)
    fun findById() {
        val expectedId: Long = 1

        var results: RealmResultsWrapper<Post>
        run { results = dao.findById(expectedId) }

        assertThat(results.results.id, `is`(expectedId))
    }

    @Test
    fun findByType() {
        val expectedType = Post.PostType.PHOTO

        var results: RealmResultsWrapper<RealmResults<Post>>
        run { results = dao.findByType(expectedType) }

        for (post in results.results) {
            assertThat(post.type, `is`(expectedType))
        }
    }

    @Test
    @Throws(Throwable::class)
    fun insert() {
        val insertedPosts = ArrayList<Post>()
        insertedPosts.add(Post())

        run { dao.insert(insertedPosts) }

        verify(mockRealm, times(1)).insertOrUpdate(insertedPosts)
        verify(mockRealm, times(1)).close()
    }

    @Test
    @Throws(Throwable::class)
    fun deleteAll() {
        run { dao.deleteAll() }

        verify(mockRealm, times(1)).delete(Post::class.java)
        verify(mockRealm, times(1)).delete(Photo::class.java)
        verify(mockRealm, times(1)).delete(AltSize::class.java)
        verify(mockRealm, times(1)).delete(Trail::class.java)
        verify(mockRealm, times(1)).delete(Blog::class.java)
        verify(mockRealm, times(1)).delete(Theme::class.java)
        verify(mockRealm, times(1)).delete(Avatar::class.java)
        verify(mockRealm, times(1)).delete(RealmString::class.java)
    }
}
