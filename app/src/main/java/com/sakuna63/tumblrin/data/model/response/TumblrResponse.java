package com.sakuna63.tumblrin.data.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class TumblrResponse<T> {

    int metaStatus;
    String metaMessage;

    T response;

    @SuppressWarnings("unchecked")
    @JsonProperty("meta")
    public void setMeta(final Map<String, Object> meta) {
        metaStatus = (int) meta.get("status");
        metaMessage = (String) meta.get("msg");
    }
}
