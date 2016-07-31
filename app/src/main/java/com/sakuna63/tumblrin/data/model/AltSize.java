package com.sakuna63.tumblrin.data.model;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class AltSize extends RealmObject {
    private Integer width;
    private Integer height;
    private String url;
}
