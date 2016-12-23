package com.sakuna63.tumbin.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.realm.RealmObject

@JsonIgnoreProperties(ignoreUnknown = true)
open class Avatar : RealmObject() {
    companion object {
        const val SIZE_16: AvatarSize = 16
        const val SIZE_24: AvatarSize = 24
        const val SIZE_32: AvatarSize = 32
        const val SIZE_40: AvatarSize = 40
        const val SIZE_48: AvatarSize = 48
        const val SIZE_64: AvatarSize = 64
        const val SIZE_96: AvatarSize = 96
        const val SIZE_128: AvatarSize = 128
        const val SIZE_512: AvatarSize = 512
    }

    var width: Int = 0
    var height: Int = 0
    lateinit var url: String
}
