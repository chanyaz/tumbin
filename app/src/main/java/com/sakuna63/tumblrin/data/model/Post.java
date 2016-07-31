package com.sakuna63.tumblrin.data.model;

import android.support.annotation.StringDef;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sakuna63.tumblrin.data.model.boxing.RealmString;
import com.sakuna63.tumblrin.data.net.deserializer.RealmStringListDeserializer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Post extends RealmObject {

    private String blogName;
    private Long id;
    private String postUrl;
    @PostType
    private String type;
    private Date date;
    private Long timestamp;
    private String format;
    private String reblogKey;
    @JsonDeserialize(using = RealmStringListDeserializer.class)
    private RealmList<RealmString> tags;
    private Integer noteCount;
    private String caption;
    private RealmList<Photo> photos;

    @StringDef({
            POST_TYPE_TEXT, POST_TYPE_QUOTE, POST_TYPE_LINK, POST_TYPE_ANSWER,
            POST_TYPE_VIDEO, POST_TYPE_AUDIO, POST_TYPE_PHOTO, POST_TYPE_CHAT
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PostType {
    }

    public static final String POST_TYPE_TEXT = "text";
    public static final String POST_TYPE_QUOTE = "quote";
    public static final String POST_TYPE_LINK = "link";
    public static final String POST_TYPE_ANSWER = "answer";
    public static final String POST_TYPE_VIDEO = "video";
    public static final String POST_TYPE_AUDIO = "audio";
    public static final String POST_TYPE_PHOTO = "photo";
    public static final String POST_TYPE_CHAT = "chat";
}
