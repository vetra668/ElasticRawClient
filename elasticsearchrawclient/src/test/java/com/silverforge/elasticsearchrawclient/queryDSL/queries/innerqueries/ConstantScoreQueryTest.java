package com.silverforge.elasticsearchrawclient.queryDSL.queries.innerQueries;

import com.silverforge.elasticsearchrawclient.BuildConfig;
import com.silverforge.elasticsearchrawclient.definition.QueryTest;
import com.silverforge.elasticsearchrawclient.definition.Queryable;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@Category(value = QueryTest.class)
public class ConstantScoreQueryTest {

    // region Happy path

    @Test
    public void when_filter_andboost_added_then_the_whole_query_is_generated() {
        Queryable matchAllQueryable = mock(Queryable.class);
        when(matchAllQueryable.getQueryString()).thenReturn("{\"match_all\":{}}");

        ConstantScoreQuery query = ConstantScoreQuery
            .builder()
            .filter(matchAllQueryable)
            .boost(1.2F)
            .build();

        String queryString = query.getQueryString();
        System.out.println(queryString);

        assertThat(queryString, notNullValue());
        assertThat(queryString, not(""));

        assertThat(queryString.startsWith("{\"constant_score\":{\"filter\":"), is(true));
        assertThat(queryString.endsWith("}}"), is(true));

        assertThat(queryString.indexOf("\"boost\":\"1.2\""), greaterThan(0));
        assertThat(queryString.indexOf("\"filter\":{\"match_all\":{}}"), greaterThan(0));
    }

    @Test
    public void when_only_filter_added_then_the_query_is_generated() {
        Queryable matchAllQueryable = mock(Queryable.class);
        when(matchAllQueryable.getQueryString()).thenReturn("{\"match_all\":{}}");

        ConstantScoreQuery query = ConstantScoreQuery
            .builder()
            .filter(matchAllQueryable)
            .build();

        String queryString = query.getQueryString();
        System.out.println(queryString);

        assertThat(queryString, notNullValue());
        assertThat(queryString, not(""));

        assertThat(queryString.startsWith("{\"constant_score\":{\"filter\":"), is(true));
        assertThat(queryString.endsWith("}}"), is(true));

        assertThat(queryString.indexOf("\"boost\""), is(-1));
    }

    // endregion

    // region Sad path

    @Test
    public void when_no_param_defined_then_empty_query_is_generated() {
        ConstantScoreQuery query = ConstantScoreQuery
            .builder()
            .build();

        String queryString = query.getQueryString();
        System.out.println(queryString);

        assertThat(queryString, notNullValue());
        assertThat(queryString, not(""));

        assertThat(queryString, is("{\"constant_score\":{}}"));
    }

    // endregion
}
