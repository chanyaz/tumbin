-dontobfuscate
-dontpreverify # "Only when eventually targeting Android, it is not necessary" https://www.guardsquare.com/proguard/manual/usage#preverificationoptions

# ----------------------------------------
# Retrofit2
# ----------------------------------------
-dontwarn retrofit2.adapter.rxjava.**
-dontwarn retrofit2.Platform$Java8

# ----------------------------------------
# OkHttp
# ----------------------------------------
-dontwarn okio.Okio
-dontwarn okio.DeflaterSink

# ----------------------------------------
# RxJava
# ----------------------------------------
-dontwarn rx.internal.util.unsafe.**

# ----------------------------------------
# Glide https://github.com/bumptech/glide/blob/master/library/proguard-rules.txt
# ----------------------------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# ----------------------------------------
# Jackson
# ----------------------------------------
-dontwarn com.fasterxml.jackson.databind.**
-keep class * extends com.fasterxml.jackson.databind.JsonDeserializer
-keep class com.sakuna63.tumbin.data.model.** { *; }