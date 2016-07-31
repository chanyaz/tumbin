package com.sakuna63.tumblrin.data.dao;

import java.io.Closeable;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;
import lombok.Getter;

public class RealmResultsWrapper<E extends RealmModel> implements Closeable {

    private final Realm realm;
    @Getter
    private final RealmResults<E> results;

    public RealmResultsWrapper(Realm realm, RealmResults<E> results) {
        this.realm = realm;
        this.results = results;
    }

    @Override
    public void close() throws IOException {
        realm.close();
    }
}
