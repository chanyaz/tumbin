package com.sakuna63.tumbin.data.model

import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class AltSize : RealmObject() {
    companion object {
        const val EXTENSION_GIF = ".gif"
    }

    var width: Int = 0
    var height: Int = 0
    lateinit var url: String

    fun isGif(): Boolean = url.endsWith(EXTENSION_GIF)
}
