package com.sakuna63.tumbin.data.model;

import android.support.annotation.StringDef;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sakuna63.tumbin.data.model.boxing.RealmString;
import com.sakuna63.tumbin.data.jackson.deserializer.RealmStringListDeserializer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Post extends RealmObject {

    public String blogName;
    @PrimaryKey
    public long id;
    public String postUrl;
    @PostType
    public String type;
    public Date date;
    public long timestamp;
    public String format;
    public String reblogKey;
    @JsonDeserialize(using = RealmStringListDeserializer.class)
    public RealmList<RealmString> tags;
    public String summary;
    public int noteCount;
    public String sourceUrl;
    public String sourceTitle;
    @JsonProperty("trail")
    public RealmList<Trail> trails;
    public String caption;
    public RealmList<Photo> photos;
    public String rebloggedFromId;
    public String rebloggedFromUrl;
    public String rebloggedFromName;
    public String rebloggedFromTitle;
    public String rebloggedFromUuid;
    public boolean rebloggedFromCanMessage;
    public boolean rebloggedFromFollowing;
    public String rebloggedRootId;
    public String rebloggedRootUrl;
    public String rebloggedRootName;
    public String rebloggedRootTitle;
    public String rebloggedRootUuid;
    public boolean rebloggedRootCanMessage;
    public boolean rebloggedRootFollowing;

    @StringDef({
            PostType.TEXT, PostType.QUOTE, PostType.LINK, PostType.ANSWER,
            PostType.VIDEO, PostType.AUDIO, PostType.PHOTO, PostType.CHAT
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PostType {
        String TEXT = "text";
        String QUOTE = "quote";
        String LINK = "link";
        String ANSWER = "answer";
        String VIDEO = "video";
        String AUDIO = "audio";
        String PHOTO = "photo";
        String CHAT = "chat";
    }
}
