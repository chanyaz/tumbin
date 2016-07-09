# ref: https://github.com/konifar/droidkaigi2016/blob/master/app/proguard-rules.pro
-keepattributes SourceFile,LineNumberTable,Exceptions
-keepnames class * extends java.lang.Throwable

# ----------------------------------------
# RxJava
# ----------------------------------------
-dontwarn rx.internal.util.unsafe.**
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}

# ----------------------------------------
# Android Support Library
# ----------------------------------------
-dontwarn android.support.**
-keep class android.support.** { *; }


# ----------------------------------------
# Retrofit and OkHttp
# ----------------------------------------
-dontwarn com.squareup.okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**

# ----------------------------------------
# Glide
# https://github.com/bumptech/glide#proguard
# ----------------------------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# ----------------------------------------
# Jackson
# https://github.com/krschultz/android-proguard-snippets/blob/master/libraries/proguard-jackson-2.pro
# ----------------------------------------
-keep class com.fasterxml.jackson.databind.ObjectMapper {
    public <methods>;
    protected <methods>;
}
-keep class com.fasterxml.jackson.databind.ObjectWriter {
    public ** writeValueAsString(**);
}
-dontwarn com.fasterxml.jackson.databind.**