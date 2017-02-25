package com.sakuna63.tumbin.application.misc

import android.support.v4.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class Arg<T> : ReadOnlyProperty<Fragment, T> {
    var value: T? = null
    var isInitialized = false

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (!isInitialized) {
            @Suppress("UNCHECKED_CAST")
            value = thisRef.arguments.get(property.name) as T
        }
        return value!!
    }
}

