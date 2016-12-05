package com.sakuna63.tumbin.data.dao

import android.support.annotation.AnyThread
import com.sakuna63.tumbin.data.model.Post
import io.realm.RealmResults

interface DashboardRealmDao {
    fun findByIdUnManaged(id: Long): Post;

    fun findById(id: Long): RealmResultsWrapper<Post>;

    fun findByTypes(@Post.PostType vararg types: String): RealmResultsWrapper<RealmResults<Post>>;

    @AnyThread
    fun insert(posts: Collection<Post>);

    fun deleteAll();
}

