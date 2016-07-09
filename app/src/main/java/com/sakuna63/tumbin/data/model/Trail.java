package com.sakuna63.tumbin.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

import io.realm.RealmObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trail extends RealmObject {

    public Blog blog;
    @JsonProperty("post")
    public long postId;
    @JsonProperty("content_raw")
    public String contentRaw;
    @JsonProperty("content")
    public String content;
    @JsonProperty("is_root_item")
    public boolean isRootItem;

    @JsonProperty("post")
    public void setPost(Map<String, Long> post) {
        postId = post.get("id");
    }
}
