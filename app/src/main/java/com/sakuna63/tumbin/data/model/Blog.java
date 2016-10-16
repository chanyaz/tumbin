package com.sakuna63.tumbin.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.realm.RealmList;
import io.realm.RealmObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Blog extends RealmObject {

    public String name;
    @JsonProperty("active")
    public boolean isActive;
    public String title;
    public String description;
    @JsonProperty("total_posts")
    public int numOfPosts;
    public String url;
    @JsonProperty("updated")
    public int updatedAtInMills;
    public String uuid;
    public String key;
    public Theme theme;
    @JsonProperty("avatar")
    public RealmList<Avatar> avatars;
    public boolean canMessage;
    public boolean shareLikes;
    public boolean shareFollowing;
    public boolean isNsfw; // = following any blog or not
    public boolean followed;
    @JsonProperty("likes")
    public int numOfLikes;
}