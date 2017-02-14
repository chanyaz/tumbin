package com.sakuna63.tumbin.application.preferences

import com.chibatching.kotpref.KotprefModel

object TokenPref : KotprefModel() {
    var token by nullableStringPref()
    var tokenSecret by nullableStringPref()
}
