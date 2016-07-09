package com.sakuna63.tumbin.data.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.sakuna63.tumbin.data.model.boxing.RealmString;

import java.io.IOException;

import io.realm.RealmList;

/**
 * ref :http://qiita.com/Koganes/items/1ab28bf31a49f0cf7dac
 */
public class RealmStringListDeserializer extends JsonDeserializer<RealmList<RealmString>> {
    @Override
    public RealmList<RealmString> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {

        if (!p.isExpectedStartArrayToken()) {
            throw new RuntimeJsonMappingException("Token does not start array.");
        }
        RealmList<RealmString> strList = new RealmList<>();
        JsonToken token;
        do {
            token = p.nextToken();
            if (token == JsonToken.VALUE_STRING) {
                strList.add(new RealmString(p.getValueAsString()));
            }
        } while (token != JsonToken.END_ARRAY);

        return strList;
    }
}
