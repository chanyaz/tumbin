package com.sakuna63.tumblrin.data.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sakuna63.tumblrin.data.model.Post;

import java.util.List;

import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class PostsResponse {
    //    private Blog blog;
    public List<Post> posts;

//    @SuppressWarnings("unchecked")
//    @JsonProperty("meta")
//    public void setMeta(final Map<String, Object> meta) {
////        metaStatus = (int) meta.get("status");
////        metaMessage = (String) meta.get("msg");
//    }

//    @SuppressWarnings("unchecked")
//    @JsonProperty("response")
//    public void setResponse(final Map<String, Object> response) {
//        posts = (List<Post>) response.get("posts");
//    }
}
