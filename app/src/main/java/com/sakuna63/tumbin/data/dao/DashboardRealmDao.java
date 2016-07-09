package com.sakuna63.tumbin.data.dao;

import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;

import com.sakuna63.tumbin.data.model.AltSize;
import com.sakuna63.tumbin.data.model.Avatar;
import com.sakuna63.tumbin.data.model.Blog;
import com.sakuna63.tumbin.data.model.Photo;
import com.sakuna63.tumbin.data.model.Post;
import com.sakuna63.tumbin.data.model.Theme;
import com.sakuna63.tumbin.data.model.Trail;
import com.sakuna63.tumbin.data.model.boxing.RealmString;

import java.util.Collection;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class DashboardRealmDao {

    @NonNull
    private final RealmConfiguration realmConfiguration;

    @Inject
    public DashboardRealmDao(@NonNull RealmConfiguration realmConfiguration) {
        this.realmConfiguration = realmConfiguration;
    }

    @NonNull
    public Post findByIdUnManaged(long id) {
        Realm realm = Realm.getInstance(realmConfiguration);
        Post post = realm.where(Post.class)
                .equalTo("id", id)
                .findFirst();
        Post unManagedPost = realm.copyFromRealm(post);
        realm.close();
        return unManagedPost;
    }

    @NonNull
    public RealmResultsWrapper<Post> findById(long id) {
        Realm realm = Realm.getInstance(realmConfiguration);
        Post post = realm.where(Post.class)
                .equalTo("id", id)
                .findFirst();
        return new RealmResultsWrapper<>(realm, post);
    }

    @NonNull
    public RealmResultsWrapper<RealmResults<Post>> findByType(@NonNull @Post.PostType String type) {
        Realm realm = Realm.getInstance(realmConfiguration);
        RealmResults<Post> results = realm.where(Post.class)
                .equalTo("type", type)
                .findAllSorted("date", Sort.DESCENDING);
        return new RealmResultsWrapper<>(realm, results);
    }

    @AnyThread
    public void insert(@NonNull final Collection<Post> posts) {
        Realm realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(posts);
            }
        });
        realm.close();
    }

    public void deleteAll() {
        Realm realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Post.class);
                realm.delete(Photo.class);
                realm.delete(AltSize.class);
                realm.delete(Trail.class);
                realm.delete(Blog.class);
                realm.delete(Theme.class);
                realm.delete(Avatar.class);
                realm.delete(RealmString.class);
            }
        });
        realm.close();
    }
}
