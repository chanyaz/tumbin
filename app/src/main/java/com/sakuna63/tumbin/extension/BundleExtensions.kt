package com.sakuna63.tumbin.extension

import android.os.Bundle
import kotlin.reflect.KProperty1

inline fun <reified T : Any> Bundle.put(prop: KProperty1<*, T>, value: T) = when (value) {
    is Long -> this.putLong(prop.name, value)
    else -> TODO("Unsupported")
}

