package com.sakuna63.tumbin.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Avatar extends RealmObject {

    public int width;
    public int height;
    public String url;
}
