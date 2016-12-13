package com.sakuna63.tumbin.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.realm.RealmObject

@JsonIgnoreProperties(ignoreUnknown = true)
open class Reblog : RealmObject() {
    lateinit var treeHtml: String
    lateinit var comment: String
}