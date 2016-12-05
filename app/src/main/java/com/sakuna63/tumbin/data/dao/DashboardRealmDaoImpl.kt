package com.sakuna63.tumbin.data.dao

import android.support.annotation.AnyThread
import com.sakuna63.tumbin.data.model.*
import com.sakuna63.tumbin.data.model.boxing.RealmString
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort
import javax.inject.Inject

class DashboardRealmDaoImpl
@Inject
constructor(private val realmConfiguration: RealmConfiguration) : DashboardRealmDao {

    override fun findByIdUnManaged(id: Long): Post {
        val realm = Realm.getInstance(realmConfiguration)
        val post = realm.where(Post::class.java).equalTo("id", id).findFirst()
        val unManagedPost = realm.copyFromRealm(post)
        realm.close()
        return unManagedPost
    }

    override fun findById(id: Long): RealmResultsWrapper<Post> {
        val realm = Realm.getInstance(realmConfiguration)
        val post = realm.where(Post::class.java).equalTo("id", id).findFirst()
        return RealmResultsWrapper(realm, post)
    }

    override fun findByTypes(@Post.PostType vararg types: String): RealmResultsWrapper<RealmResults<Post>> {
        val realm = Realm.getInstance(realmConfiguration)
        var query = realm.where(Post::class.java)
        for ((i, type) in types.withIndex()) {
            if (i != 0) query = query.or()
            query = query.equalTo("type", type)
        }
        val results = query.findAllSorted("date", Sort.DESCENDING)
        return RealmResultsWrapper(realm, results)
    }

    @AnyThread
    override fun insert(posts: Collection<Post>) {
        val realm = Realm.getInstance(realmConfiguration)
        realm.executeTransaction { it.copyToRealmOrUpdate(posts) }
        realm.close()
    }

    override fun deleteAll() {
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
