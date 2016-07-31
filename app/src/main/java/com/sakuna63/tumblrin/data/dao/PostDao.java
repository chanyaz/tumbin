package com.sakuna63.tumblrin.data.dao;

import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;

import com.sakuna63.tumblrin.data.model.Post;

import java.util.Collection;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class PostDao {

    @NonNull
    private final RealmConfiguration realmConfiguration;

    @Inject
    public PostDao(@NonNull RealmConfiguration realmConfiguration) {
        this.realmConfiguration = realmConfiguration;
    }

    @AnyThread
    public void insert(final Collection<Post> posts) {
        Realm realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(posts);
            }
        });
        realm.close();
    }

    @AnyThread
    public RealmResultsWrapper<Post> findByType(@Post.PostType String type) {
        Realm realm = Realm.getInstance(realmConfiguration);
        RealmResults<Post> results = realm.where(Post.class)
                .equalTo("type", type)
                .findAllSorted("date", Sort.DESCENDING);
        return new RealmResultsWrapper<>(realm, results);
    }
}
