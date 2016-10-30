package com.sakuna63.tumbin.data.model

import io.realm.RealmObject

open class Reblog : RealmObject() {
    lateinit var treeHtml: String
    lateinit var comment: String
}