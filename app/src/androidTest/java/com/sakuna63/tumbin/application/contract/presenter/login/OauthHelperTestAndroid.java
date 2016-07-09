package com.sakuna63.tumbin.application.contract.presenter.login;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class OauthHelperTestAndroid {

    @Test
    public void extractVerifier_includeOauthVerifier_verifier() throws Throwable {
        String expected = "verifier";
        String inputUrl = "http://example.com?oauth_verifier=" + expected;

        String actual;
        {
            actual = OauthHelper.extractVerifier(inputUrl);
        }

        assertThat(actual, is(expected));
    }

    @Test
    public void extractVerifier_notIncludeOauthVerifier_null() throws Throwable {
        String expected = null;
        String inputUrl = "http://example.com";

        String actual;
        {
            actual = OauthHelper.extractVerifier(inputUrl);
        }

        assertThat(actual, is(expected));
    }

    @Test
    public void extractVerifier_notUrlForm_null() throws Throwable {
        String expected = null;
        String inputUrl = "input";

        String actual;
        {
            actual = OauthHelper.extractVerifier(inputUrl);
        }

        assertThat(actual, is(expected));
    }
}