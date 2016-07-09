package com.sakuna63.tumbin.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.realm.RealmObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Theme extends RealmObject {

    @JsonProperty("avatar_shape")
    public String avatarShape;
    @JsonProperty("background_color")
    public String backgroundColor;
    @JsonProperty("body_font")
    public String bodyFont;
    @JsonProperty("header_bounds")
    public String headerBounds;
    @JsonProperty("header_image")
    public String headerImage;
    @JsonProperty("header_image_focused")
    public String headerImageFocused;
    @JsonProperty("header_image_scaled")
    public String headerImageScaled;
    @JsonProperty("header_stretch")
    public boolean headerStretch;
    @JsonProperty("link_color")
    public String linkColor;
    @JsonProperty("show_avatar")
    public boolean showAvatar;
    @JsonProperty("show_description")
    public boolean showDescription;
    @JsonProperty("show_header_image")
    public boolean showHeaderImage;
    @JsonProperty("show_title")
    public boolean showTitle;
    @JsonProperty("title_color")
    public String titleColor;
    @JsonProperty("title_font")
    public String titleFont;
    @JsonProperty("title_font_weight")
    public String titleFontWeight;
}
