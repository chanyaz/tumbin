package com.sakuna63.tumbin.data.model.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.sakuna63.tumbin.data.model.Blog
import com.sakuna63.tumbin.data.model.Post

@JsonIgnoreProperties(ignoreUnknown = true)
class PostsResponse
@JsonCreator
constructor(@JsonProperty("blog") val blog: Blog?, @JsonProperty("posts") val posts: List<Post>)

