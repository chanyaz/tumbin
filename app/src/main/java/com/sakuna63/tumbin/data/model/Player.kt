package com.sakuna63.tumbin.data.model

import io.realm.RealmObject

open class Player : RealmObject() {
    var width: Int = -1
    lateinit var embedCode: String // e.g. <video id=...
}
