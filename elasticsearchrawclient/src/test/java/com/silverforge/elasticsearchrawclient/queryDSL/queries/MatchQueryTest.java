package com.silverforge.elasticsearchrawclient.queryDSL.queries;

import com.silverforge.elasticsearchrawclient.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MatchQueryTest {

    @Test
    public void defaultMatchBuilderTest() {
        MatchQuery matchQuery =
            MatchQuery
                .builder()
                .fieldName("name")
                .value("Karcag")
                .build();

        String queryString = matchQuery.getQueryString();

        assertThat(queryString, notNullValue());
        assertThat(queryString, not(""));
        assertThat(queryString, is("{\"query\" : {\"match\" : {\"name\" : \"Karcag\"}}}"));
    }
}