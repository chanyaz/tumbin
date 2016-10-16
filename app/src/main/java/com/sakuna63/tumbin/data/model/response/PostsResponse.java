package com.sakuna63.tumbin.data.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sakuna63.tumbin.data.model.Blog;
import com.sakuna63.tumbin.data.model.Post;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostsResponse {
    private Blog blog;

    public Blog getBlog() {
        return blog;
    }
}
