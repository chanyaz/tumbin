package com.sakuna63.tumbin.data.dao;

import com.sakuna63.tumbin.data.model.AltSize;
import com.sakuna63.tumbin.data.model.Avatar;
import com.sakuna63.tumbin.data.model.Blog;
import com.sakuna63.tumbin.data.model.Photo;
import com.sakuna63.tumbin.data.model.Post;
import com.sakuna63.tumbin.data.model.Theme;
import com.sakuna63.tumbin.data.model.Trail;
import com.sakuna63.tumbin.data.model.boxing.RealmString;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.internal.RealmCore;
import io.realm.log.RealmLog;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@PrepareForTest({Realm.class, RealmConfiguration.class, RealmQuery.class, RealmResults.class, RealmCore.class, RealmLog.class})
public class DashboardRealmDaoTest {
    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    DashboardRealmDao dao;

    Realm mockRealm;

    @Before
    public void setUp() throws Exception {
        mockStatic(RealmCore.class);
        mockStatic(RealmLog.class);
        mockStatic(Realm.class);
        mockStatic(RealmConfiguration.class);
        mockStatic(RealmQuery.class);
        mockStatic(RealmResults.class);

        dao = new DashboardRealmDao(null);

        mockRealm = PowerMockito.mock(Realm.class);
        RealmQuery<Post> mockRealmQuery = PowerMockito.mock(RealmQuery.class);
        RealmResults<Post> mockRealmResults = PowerMockito.mock(RealmResults.class);
        PowerMockito.when(Realm.getInstance((RealmConfiguration) any())).thenReturn(mockRealm);

        when(mockRealm.where(Post.class)).thenReturn(mockRealmQuery);
        doCallRealMethod().when(mockRealm).executeTransaction(Mockito.any(Realm.Transaction.class));

        Post post1 = new Post();
        post1.id = 1;
        post1.type = Post.PostType.PHOTO;

        Post post2 = new Post();
        post2.id = 2;
        post2.type = Post.PostType.PHOTO;

        Post post3 = new Post();
        post3.id = 3;
        post3.type = Post.PostType.TEXT;

        Post post4 = new Post();
        post4.id = 4;
        post4.type = Post.PostType.ANSWER;


        List<Post> posts = Arrays.asList(post1, post2, post3, post4);
        List<Post> photoPosts = posts.subList(0, 2);

        // for id matching
        when(mockRealmQuery.equalTo("id", 1L)).thenReturn(mockRealmQuery);
        when(mockRealmQuery.findFirst()).thenReturn(post1);

        // for type matching
        when(mockRealmQuery.equalTo("type", Post.PostType.PHOTO)).thenReturn(mockRealmQuery);
        when(mockRealmQuery.findAllSorted(anyString(), Matchers.<Sort>any())).thenReturn(mockRealmResults);
        when(mockRealmResults.iterator()).thenReturn(photoPosts.iterator());
    }

    @Test
    public void findById() throws Throwable {
        long expectedId = 1;

        RealmResultsWrapper<Post> results;
        {
            results = dao.findById(expectedId);
        }

        assertThat(results.getResults().id, is(expectedId));
    }

    @Test
    public void findByType() {
        String expectedType = Post.PostType.PHOTO;

        RealmResultsWrapper<RealmResults<Post>> results;
        {
            results = dao.findByType(expectedType);
        }

        for (Post post : results.getResults()) {
            assertThat(post.type, is(expectedType));
        }
    }

    @Test
    public void insert() throws Throwable {
        List<Post> insertedPosts = new ArrayList<>();
        insertedPosts.add(new Post());

        {
            dao.insert(insertedPosts);
        }

        verify(mockRealm, times(1)).insertOrUpdate(insertedPosts);
        verify(mockRealm, times(1)).close();
    }

    @Test
    public void deleteAll() throws Throwable {
        {
            dao.deleteAll();
        }

        verify(mockRealm, times(1)).delete(Post.class);
        verify(mockRealm, times(1)).delete(Photo.class);
        verify(mockRealm, times(1)).delete(AltSize.class);
        verify(mockRealm, times(1)).delete(Trail.class);
        verify(mockRealm, times(1)).delete(Blog.class);
        verify(mockRealm, times(1)).delete(Theme.class);
        verify(mockRealm, times(1)).delete(Avatar.class);
        verify(mockRealm, times(1)).delete(RealmString.class);
    }
}