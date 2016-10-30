package com.sakuna63.tumbin.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.realm.RealmObject

@JsonIgnoreProperties(ignoreUnknown = true)
open class Trail : RealmObject() {

    lateinit var blog: Blog
    var postId: Long = 0
    lateinit var contentRaw: String
    lateinit var content: String
    var isRootItem: Boolean = false

    @JsonProperty("post")
    fun setPost(post: Map<String, Long>) {
        postId = post["id"]!!
    }
}
