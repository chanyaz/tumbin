package com.sakuna63.tumbin.data.dao

import android.support.annotation.AnyThread
import com.sakuna63.tumbin.data.model.*
import com.sakuna63.tumbin.data.model.boxing.RealmString
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort
import javax.inject.Inject

class DashboardRealmDao
@Inject
constructor(private val realmConfiguration: RealmConfiguration) {

    fun findByIdUnManaged(id: Long): Post {
        val realm = Realm.getInstance(realmConfiguration)
        val post = realm.where(Post::class.java).equalTo("id", id).findFirst()
        val unManagedPost = realm.copyFromRealm(post)
        realm.close()
        return unManagedPost
    }

    fun findById(id: Long): RealmResultsWrapper<Post> {
        val realm = Realm.getInstance(realmConfiguration)
        val post = realm.where(Post::class.java).equalTo("id", id).findFirst()
        return RealmResultsWrapper(realm, post)
    }

    fun findByType(@Post.PostType type: String): RealmResultsWrapper<RealmResults<Post>> {
        val realm = Realm.getInstance(realmConfiguration)
        val results = realm.where(Post::class.java).equalTo("type", type)
                .findAllSorted("date", Sort.DESCENDING)
        return RealmResultsWrapper(realm, results)
    }

    @AnyThread
    fun insert(posts: Collection<Post>) {
        val realm = Realm.getInstance(realmConfiguration)
        realm.executeTransaction { it.copyToRealmOrUpdate(posts) }
        realm.close()
    }

    fun deleteAll() {
        val realm = Realm.getInstance(realmConfiguration)
        realm.executeTransaction {
            it.delete(Post::class.java)
            it.delete(Photo::class.java)
            it.delete(AltSize::class.java)
            it.delete(Trail::class.java)
            it.delete(Blog::class.java)
            it.delete(Avatar::class.java)
            it.delete(RealmString::class.java)
            it.delete(Reblog::class.java)
        }
        realm.close()
    }
}
