package com.sakuna63.tumbin.data.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TumblrResponse<T> {

    private int metaStatus;
    private String metaMessage;

    private T response;

    @SuppressWarnings("unchecked")
    @JsonProperty("meta")
    public void setMeta(final Map<String, Object> meta) {
        metaStatus = (int) meta.get("status");
        metaMessage = (String) meta.get("msg");
    }

    public int getMetaStatus() {
        return metaStatus;
    }

    public String getMetaMessage() {
        return metaMessage;
    }

    public T getResponse() {
        return response;
    }
}
