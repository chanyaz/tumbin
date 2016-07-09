package com.sakuna63.tumbin.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.realm.RealmList;
import io.realm.RealmObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Blog extends RealmObject {

    public String name;
    public boolean active;
    public String title;
    public String description;
    public String url;
    public int updated;
    public String uuid;
    public String key;
    public Theme theme;
    @JsonProperty("avatar")
    public RealmList<Avatar> avatars;
    public boolean canMessage;
    public boolean shareLikes;
    public boolean shareFollowing;
    public boolean isNsfw;
}
