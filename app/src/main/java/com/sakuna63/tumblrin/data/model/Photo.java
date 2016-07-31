package com.sakuna63.tumblrin.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Photo extends RealmObject {
    @JsonProperty("caption")
    private String caption;
    @JsonProperty("alt_sizes")
    private RealmList<AltSize> altSizes;
}
