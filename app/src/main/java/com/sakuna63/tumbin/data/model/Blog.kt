package com.sakuna63.tumbin.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.realm.RealmList
import io.realm.RealmObject

@JsonIgnoreProperties(ignoreUnknown = true)
open class Blog : RealmObject() {

    lateinit var name: String
    var active: Boolean = false
    var title: String? = null
    var description: String? = null
    var url: String? = null
    var updated: Int = 0
    var uuid: String? = null
    var key: String? = null
//    lateinit var theme: Theme
    @JsonProperty("avatar")
    var avatars: RealmList<Avatar>? = null
    var canMessage: Boolean = false
    var shareLikes: Boolean = false
    var shareFollowing: Boolean = false
    var canBeFollowed: Boolean = false
    var isNsfw: Boolean = false
}
