package com.sakuna63.tumbin.data.model

import android.support.annotation.StringDef
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.sakuna63.tumbin.data.jackson.deserializer.RealmStringListDeserializer
import com.sakuna63.tumbin.data.model.boxing.RealmString
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
open class Post : RealmObject() {

    companion object {
        const val TYPE_TEXT = "text"
        const val TYPE_QUOTE = "quote"
        const val TYPE_LINK = "link"
        const val TYPE_ANSWER = "answer"
        const val TYPE_VIDEO = "video"
        const val TYPE_AUDIO = "audio"
        const val TYPE_PHOTO = "photo"
        const val TYPE_CHAT = "chat"

        const val FORMAT_PLAIN = "plain"
        const val FORMAT_HTML = "html"
        const val FORMAT_MARKDOWN = "markdown"

        const val STATE_PUBLISHED = "published"

        const val VIDEO_TYPE_TUMBLR = "tumblr"
        const val VIDEO_TYPE_YOUTUBE = "youtube"
        const val VIDEO_TYPE_FLICKR = "flickr"
        const val VIDEO_TYPE_INSTAGRAM = "instagram"
        const val VIDEO_TYPE_VINE = "instagram"
    }

    ////////////////////////////////////////
    // Really NonNull attributes
    ////////////////////////////////////////

    lateinit var blogName: String
    @PrimaryKey
    var id: Long = -1;
    lateinit var postUrl: String
    lateinit var slug: String
    @PostType
    lateinit var type: String
    lateinit var date: Date
    var timestamp: Long = 0
    @State
    lateinit var state: String
    @Format
    lateinit var format: String
    lateinit var reblogKey: String
    @JsonDeserialize(using = RealmStringListDeserializer::class)
    lateinit var tags: RealmList<RealmString>
    lateinit var shortUrl: String
    lateinit var summary: String
    var followed: Boolean = false
    var liked: Boolean = false
    var noteCount: Int = 0
    lateinit var caption: String
    lateinit var reblog: Reblog
    @JsonProperty("trail")
    lateinit var trails: RealmList<Trail>
    var canLike: Boolean = false
    var canReblog: Boolean = false
    var canSendInMessage: Boolean = false
    var canReply: Boolean = false
    var displayAvatar: Boolean = false

    ////////////////////////////////////////
    // Really Nullable attributes
    ////////////////////////////////////////
    var recommendedSource: String? = null
    var recommendedColor: String? = null
    var sourceUrl: String? = null
    var sourceTitle: String? = null
    var linkUrl: String? = null

    ////////////////////////////////////////
    // "photo" post attributes
    ////////////////////////////////////////
    var photos: RealmList<Photo>? = null
    var imagePermalink: String? = null
    // "111" means 3 lines with 1 column
    // "211" means first line has 2 columns, others have 1 column
    var photoSetLayout: String? = null

    ////////////////////////////////////////
    // "text" post attributes
    ////////////////////////////////////////
    var title: String? = null
    var body: String? = null

    ////////////////////////////////////////
    // "video" post attributes
    ////////////////////////////////////////
    var videoUrl: String? = null
    var permalinkUrl: String? = null
    var html5Capable: Boolean = false
    var video: Video? = null
    var thumbnailUrl: String? = null
    var thumbnailHeight: Int = -1
    var thumbnailWidth: Int = -1
    var duration: Int = -1
    //    @JsonProperty("player")
//    var players: RealmList<Player>? = null
    @VideoType
    var videoType: String? = null


    ////////////////////////////////////////
    // returned by "notes_info = true"
    ////////////////////////////////////////
    // lateinit var notes: List<Note>

    ////////////////////////////////////////
    // returned by "reblog_info = true"
    ////////////////////////////////////////
    var rebloggedFromId: Long = 0
    var rebloggedFromUrl: String? = null
    var rebloggedFromName: String? = null
    var rebloggedFromTitle: String? = null
    var rebloggedFromUuid: String? = null
    var rebloggedFromCanMessage: Boolean = false
    var rebloggedFromFollowing: Boolean = false
    var rebloggedRootId: String? = null
    var rebloggedRootUrl: String? = null
    var rebloggedRootName: String? = null
    var rebloggedRootTitle: String? = null
    var rebloggedRootUuid: String? = null
    var rebloggedRootCanMessage: Boolean = false
    var rebloggedRootFollowing: Boolean = false


    @StringDef(Post.TYPE_TEXT, Post.TYPE_QUOTE, Post.TYPE_LINK, Post.TYPE_ANSWER,
            Post.TYPE_VIDEO, Post.TYPE_AUDIO, Post.TYPE_PHOTO, Post.TYPE_CHAT)
    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    annotation class PostType

    @StringDef(Post.VIDEO_TYPE_TUMBLR, Post.VIDEO_TYPE_YOUTUBE,
            Post.VIDEO_TYPE_FLICKR, Post.VIDEO_TYPE_INSTAGRAM, Post.VIDEO_TYPE_VINE)
    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    annotation class VideoType

    @StringDef(Post.FORMAT_HTML)
    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Format

    @StringDef(Post.STATE_PUBLISHED)
    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    annotation class State
}
