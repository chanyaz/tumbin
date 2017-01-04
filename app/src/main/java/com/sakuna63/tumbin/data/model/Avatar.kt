package com.sakuna63.tumbin.data.model

import android.support.annotation.IntDef
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.realm.RealmObject

@JsonIgnoreProperties(ignoreUnknown = true)
open class Avatar : RealmObject() {
    companion object {
        const val SIZE_16 = 16L
        const val SIZE_24 = 24L
        const val SIZE_32 = 32L
        const val SIZE_40 = 40L
        const val SIZE_48 = 48L
        const val SIZE_64 = 64L
        const val SIZE_96 = 96L
        const val SIZE_128 = 128L
        const val SIZE_512 = 512L
    }

    var width: Int = 0
    var height: Int = 0
    lateinit var url: String

    @IntDef(
            Avatar.SIZE_16, Avatar.SIZE_24, Avatar.SIZE_32, Avatar.SIZE_40,
            Avatar.SIZE_48, Avatar.SIZE_64, Avatar.SIZE_96, Avatar.SIZE_128, Avatar.SIZE_512
    )
    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Size
}
