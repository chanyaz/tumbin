package com.sakuna63.tumbin.application.contract.presenter.login

import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

@RunWith(AndroidJUnit4::class)
@SmallTest
class OauthHelperTestAndroid {

    @Test
    @Throws(Throwable::class)
    fun extractVerifier_includeOauthVerifier_verifier() {
        val expected = "verifier"
        val inputUrl = "http://example.com?oauth_verifier=" + expected

        var actual: String
        run { actual = OauthHelper.extractVerifier(inputUrl) }

        assertThat(actual, `is`(expected))
    }

    @Test
    @Throws(Throwable::class)
    fun extractVerifier_notIncludeOauthVerifier_null() {
        val expected: String? = null
        val inputUrl = "http://example.com"

        var actual: String
        run { actual = OauthHelper.extractVerifier(inputUrl) }

        assertThat(actual, `is`<String>(expected))
    }

    @Test
    @Throws(Throwable::class)
    fun extractVerifier_notUrlForm_null() {
        val expected: String? = null
        val inputUrl = "input"

        var actual: String
        run { actual = OauthHelper.extractVerifier(inputUrl) }

        assertThat(actual, `is`<String>(expected))
    }
}