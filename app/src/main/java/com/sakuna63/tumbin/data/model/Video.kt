package com.sakuna63.tumbin.data.model

import io.realm.RealmObject

open class Video : RealmObject() {
    lateinit var youtube: Youtube
}

open class Youtube : RealmObject() {
    lateinit var videoId: String
    var width: Int = -1
    var height: Int = -1
}
