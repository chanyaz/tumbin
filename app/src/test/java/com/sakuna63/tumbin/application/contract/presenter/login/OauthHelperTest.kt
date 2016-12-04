package com.sakuna63.tumbin.application.contract.presenter.login

import com.sakuna63.tumbin.BuildConfig
import com.sakuna63.tumbin.test.TestApp
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(23), application = TestApp::class)
class OauthHelperTest {

    @Test
    @Throws(Throwable::class)
    fun extractVerifier_includeOauthVerifier_verifier() {
        val expected = "verifier"
        val inputUrl = "http://example.com?oauth_verifier=" + expected
        val actual = OauthHelper.extractVerifier(inputUrl)
        assertThat(actual, `is`(expected))
    }

    @Test
    @Throws(Throwable::class)
    fun extractVerifier_notIncludeOauthVerifier_null() {
        val expected: String? = null
        val inputUrl = "http://example.com"
        val actual = OauthHelper.extractVerifier(inputUrl)
        assertThat(actual, `is`<String>(expected))
    }

    @Test
    @Throws(Throwable::class)
    fun extractVerifier_notUrlForm_null() {
        val expected: String? = null
        val inputUrl = "input"
        val actual = OauthHelper.extractVerifier(inputUrl)
        assertThat(actual, `is`<String>(expected))
    }
}