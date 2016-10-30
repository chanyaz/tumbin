package com.sakuna63.tumbin.data.jackson.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.RuntimeJsonMappingException
import com.sakuna63.tumbin.data.model.boxing.RealmString

import java.io.IOException

import io.realm.RealmList

/**
 * ref :http://qiita.com/Koganes/items/1ab28bf31a49f0cf7dac
 */
class RealmStringListDeserializer : JsonDeserializer<RealmList<RealmString>>() {
    @Throws(IOException::class)
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): RealmList<RealmString> {

        if (!p.isExpectedStartArrayToken) {
            throw RuntimeJsonMappingException("Token does not start array.")
        }
        val strList = RealmList<RealmString>()
        var token: JsonToken
        do {
            token = p.nextToken()
            if (token == JsonToken.VALUE_STRING) {
                strList.add(RealmString(p.valueAsString))
            }
        } while (token != JsonToken.END_ARRAY)

        return strList
    }
}
