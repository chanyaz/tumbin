package com.sakuna63.tumbin.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@JsonIgnoreProperties(ignoreUnknown = true)
open class Photo : RealmObject() {
    lateinit var caption: String
    lateinit var altSizes: RealmList<AltSize>
}
