package com.sakuna63.tumbin.data.dao;

import android.support.annotation.NonNull;

import java.io.Closeable;

import io.realm.Realm;

public class RealmResultsWrapper<E> implements Closeable {

    @NonNull
    private final Realm realm;
    @NonNull
    private final E results;

    public RealmResultsWrapper(@NonNull Realm realm, @NonNull E results) {
        this.realm = realm;
        this.results = results;
    }

    @Override
    public void close() {
        realm.close();
    }

    @NonNull
    public E getResults() {
        return results;
    }
}
