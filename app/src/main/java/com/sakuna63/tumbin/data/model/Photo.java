package com.sakuna63.tumbin.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmList;
import io.realm.RealmObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo extends RealmObject {
    public String caption;
    public RealmList<AltSize> altSizes;
}
