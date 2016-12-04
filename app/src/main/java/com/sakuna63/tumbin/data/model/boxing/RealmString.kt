package com.sakuna63.tumbin.data.model.boxing

import io.realm.RealmObject

open class RealmString() : RealmObject() {
    lateinit var value: String

    constructor(value: String) : this() {
        this.value = value
    }
}
