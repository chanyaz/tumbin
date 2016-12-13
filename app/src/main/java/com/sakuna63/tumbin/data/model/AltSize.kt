package com.sakuna63.tumbin.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.realm.RealmObject

@JsonIgnoreProperties(ignoreUnknown = true)
open class AltSize : RealmObject() {
    companion object {
        const val EXTENSION_GIF = ".gif"
    }

    var width: Int = 0
    var height: Int = 0
    lateinit var url: String

    fun isGif(): Boolean = url.endsWith(EXTENSION_GIF)
}
