package com.sakuna63.tumbin.data.dao

import com.sakuna63.tumbin.data.model.*
import com.sakuna63.tumbin.data.model.boxing.RealmString
import io.realm.*
import io.realm.internal.RealmCore
import io.realm.log.RealmLog
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Matchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.mockStatic
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.rule.PowerMockRule
import java.util.*

@PrepareForTest(Realm::class, RealmConfiguration::class, RealmQuery::class, RealmResults::class, RealmCore::class, RealmLog::class)
class DashboardRealmDaoImplTest {
    @Rule
    @JvmField
    val powerMockRule = PowerMockRule()

    lateinit var dao: DashboardRealmDaoImpl

    lateinit var mockRealm: Realm

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mockStatic(RealmCore::class.java)
        mockStatic(RealmLog::class.java)
        mockStatic(Realm::class.java)
        mockStatic(RealmConfiguration::class.java)
        mockStatic(RealmQuery::class.java)
        mockStatic(RealmResults::class.java)

        val mockRealmConfiguration = PowerMockito.mock(RealmConfiguration::class.java)
        dao = DashboardRealmDaoImpl(mockRealmConfiguration)

        mockRealm = PowerMockito.mock(Realm::class.java)
        @Suppress("UNCHECKED_CAST")
        val mockRealmQuery = PowerMockito.mock(RealmQuery::class.java) as RealmQuery<Post>
        @Suppress("UNCHECKED_CAST")
        val mockRealmResults = PowerMockito.mock(RealmResults::class.java) as RealmResults<Post>

        PowerMockito.`when`(Realm.getInstance(mockRealmConfiguration)).thenReturn(mockRealm)

        `when`(mockRealm.where(Post::class.java)).thenReturn(mockRealmQuery)
        doCallRealMethod().`when`(mockRealm).executeTransaction(Mockito.any(Realm.Transaction::class.java))

        val post1 = Post()
        post1.id = 1
        post1.type = Post.TYPE_PHOTO

        val post2 = Post()
        post2.id = 2
        post2.type = Post.TYPE_PHOTO

        val post3 = Post()
        post3.id = 3
        post3.type = Post.TYPE_TEXT

        val post4 = Post()
        post4.id = 4
        post4.type = Post.TYPE_ANSWER


        val posts = Arrays.asList(post1, post2, post3, post4)
        val photoPosts = posts.subList(0, 2)

        // for id matching
        `when`<RealmQuery<Post>>(mockRealmQuery.equalTo("id", 1L)).thenReturn(mockRealmQuery)
        `when`<Post>(mockRealmQuery.findFirst()).thenReturn(post1)

        // for type matching
        `when`<RealmQuery<Post>>(mockRealmQuery.equalTo("type", Post.TYPE_PHOTO)).thenReturn(mockRealmQuery)
        `when`<RealmResults<Post>>(mockRealmQuery.findAllSorted(anyString(), Matchers.any<Sort>())).thenReturn(mockRealmResults)
        `when`<Iterator<Post>>(mockRealmResults.iterator()).thenReturn(photoPosts.iterator())
    }

    @Test
    @Throws(Throwable::class)
    fun findById() {
        val expectedId: Long = 1
        val results = dao.findById(expectedId)
        assertThat(results.results.id, `is`(expectedId))
    }

    @Test
    fun findByType() {
        val expectedType = Post.TYPE_PHOTO
        val results = dao.findByType(expectedType)
        for (post in results.results) {
            assertThat(post.type, `is`(expectedType))
        }
    }

    @Test
    @Throws(Throwable::class)
    fun insert() {
        val insertedPosts = ArrayList<Post>()
        insertedPosts.add(Post())

        dao.insert(insertedPosts)

        verify(mockRealm, times(1)).copyToRealmOrUpdate(insertedPosts)
        verify(mockRealm, times(1)).close()
    }

    @Test
    @Throws(Throwable::class)
    fun deleteAll() {
        dao.deleteAll()

        verify(mockRealm).delete(Post::class.java)
        verify(mockRealm).delete(Photo::class.java)
        verify(mockRealm).delete(AltSize::class.java)
        verify(mockRealm).delete(Trail::class.java)
        verify(mockRealm).delete(Blog::class.java)
        verify(mockRealm).delete(Avatar::class.java)
        verify(mockRealm).delete(RealmString::class.java)
    }
}
