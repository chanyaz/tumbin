package com.sakuna63.tumbin.data.dao

import io.realm.Realm
import java.io.Closeable

class RealmResultsWrapper<out E>(private val realm: Realm, val results: E) : Closeable {

    override fun close() {
        realm.close()
    }
}
