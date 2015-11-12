package com.silverforge.elasticsearchrawclient.queryDSL.queries.innerQueries;

import android.text.TextUtils;
import com.silverforge.elasticsearchrawclient.exceptions.MandatoryParametersAreMissingException;
import com.silverforge.elasticsearchrawclient.model.QueryTypeItem;
import com.silverforge.elasticsearchrawclient.queryDSL.Constants;
import com.silverforge.elasticsearchrawclient.queryDSL.generator.QueryFactory;
import com.silverforge.elasticsearchrawclient.queryDSL.queries.innerQueries.common.BoostQuery;
import com.silverforge.elasticsearchrawclient.utils.QueryTypeArrayList;
import java.util.ArrayList;
import java.util.List;
import static br.com.zbra.androidlinq.Linq.stream;

public class WildCardQuery
        extends BoostQuery {

    private QueryTypeArrayList<QueryTypeItem> queryBag;

    WildCardQuery(QueryTypeArrayList<QueryTypeItem> queryBag) {
        this.queryBag = queryBag;
    }

    @Override
    public String getQueryString() {
        return QueryFactory
                .wildCardQueryGenerator()
                .generate(queryBag);
    }

    public static Init<?> builder() {
        return new WildCardQueryBuilder();
    }

    public static class WildCardQueryBuilder extends Init<WildCardQueryBuilder> {
        @Override
        protected WildCardQueryBuilder self() {
            return this;
        }
    }

    public static abstract class Init<T extends Init<T>>
            extends BoostQuery.BoostInit<T> {

        public T fieldName(String fieldName) {
            if (TextUtils.isEmpty(fieldName))
                return allFields();

            queryBag.addParentItem(Constants.FIELD_NAME, fieldName);
            return self();
        }

        public T allFields() {
            queryBag.addParentItem(Constants.FIELD_NAME, "_all");
            return self();
        }

        public T value(String value) {
            queryBag.addItem(Constants.VALUE, value);
            return self();
        }

        public T wildcard(String wildcard) {
            queryBag.addItem(Constants.WILDCARD, wildcard);
            return self();
        }

        public WildCardQuery build() throws MandatoryParametersAreMissingException {
            List<String> missingParams = new ArrayList<>();

            if(!queryBag.containsKey(Constants.FIELD_NAME))
                missingParams.add(Constants.FIELD_NAME);
            if(stream(missingParams).count() > 0)
                throw new MandatoryParametersAreMissingException(missingParams.toString());

            return new WildCardQuery(queryBag);
        }

    }
}
