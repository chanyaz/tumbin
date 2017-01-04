package com.sakuna63.tumbin.data.model.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class TumblrResponse<out T>
@JsonCreator
constructor(@JsonProperty("response") val response: T)
