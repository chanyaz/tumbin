package com.sakuna63.tumbin.test

import android.app.Application

/**
 * suppress loading native libraries of realm
 *
 * @see https://github.com/realm/realm-java/pull/1867
 * @see https://github.com/realm/realm-java/issues/2500
 * @see https://github.com/robolectric/robolectric/issues/1171
 */
class TestApp : Application()
