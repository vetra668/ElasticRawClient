package com.silverforge.elasticsearchrawclient.queryDSL.queries.innerqueries;

import com.silverforge.elasticsearchrawclient.BuildConfig;
import com.silverforge.elasticsearchrawclient.queryDSL.operators.MultiMatchTypeOperator;
import com.silverforge.elasticsearchrawclient.queryDSL.operators.ZeroToOneRangeOperator;
import com.silverforge.elasticsearchrawclient.queryDSL.queries.innerqueries.MultiMatchQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MultiMatchQueryTest {

    // region Happy path

    @Test
    public void when_fields_value_added_then_fields_generated_well() {
        MultiMatchQuery query = MultiMatchQuery
            .builder()
            .fields(new String[]{"name", "population"})
            .query("12")
            .build();

        String queryString = query.getQueryString();

        assertThat(queryString, notNullValue());
        assertThat(queryString, not(""));

        assertThat(queryString.indexOf("{\"multi_match\":{"), is(0));
        assertThat(queryString.indexOf("\"query\":\"12\""), greaterThan(0));
        assertThat(queryString.indexOf("\"fields\":[\"name\",\"population\"]"), greaterThan(0));
        assertThat(queryString.indexOf("}}"), greaterThan(0));
    }

    @Test
    public void when_all_fields_value_added_then_fields_generated_well() {
        MultiMatchQuery query = MultiMatchQuery
            .builder()
            .fields(new String[]{"name", "population"})
            .query("Karcag Budapest")
            .tieBreaker(ZeroToOneRangeOperator._0_3)
            .useDisMax(true)
            .type(MultiMatchTypeOperator.BEST_FIELDS)
            .minimumShouldMatch(3)
            .build();

        String queryString = query.getQueryString();

        assertThat(queryString, notNullValue());
        assertThat(queryString, not(""));

        assertThat(queryString.indexOf("{\"multi_match\":{"), is(0));
        assertThat(queryString.indexOf("\"query\":\"Karcag Budapest\""), greaterThan(0));
        assertThat(queryString.indexOf("\"fields\":[\"name\",\"population\"]"), greaterThan(0));
        assertThat(queryString.indexOf("\"tie_breaker\":\"0.3\""), greaterThan(0));
        assertThat(queryString.indexOf("\"use_dis_max\":\"true\""), greaterThan(0));
        assertThat(queryString.indexOf("\"type\":\"best_fields\""), greaterThan(0));
        assertThat(queryString.indexOf("\"minimum_should_match\":\"3\""), greaterThan(0));

        assertThat(queryString.indexOf("\",\""), greaterThan(0));
        assertThat(queryString.indexOf("\"\""), is(-1));
        assertThat(queryString.indexOf("}}"), greaterThan(0));
    }

    // endregion

    // region Sad path

    @Test
    public void when_no_parameters_defined_then_query_is_empty() {
        MultiMatchQuery query = MultiMatchQuery
            .builder()
            .build();

        String queryString = query.getQueryString();

        assertThat(queryString, notNullValue());
        assertThat(queryString, not(""));

        assertThat(queryString, is("{\"multi_match\":{}}"));
    }

    //
}
