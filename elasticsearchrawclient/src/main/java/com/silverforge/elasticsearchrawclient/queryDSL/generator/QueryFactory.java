package com.silverforge.elasticsearchrawclient.queryDSL.generator;

import com.silverforge.elasticsearchrawclient.model.QueryTypeItem;
import com.silverforge.elasticsearchrawclient.queryDSL.queries.innerqueries.ConstantScoreQuery;
import com.silverforge.elasticsearchrawclient.utils.QueryTypeArrayList;

import java.util.Map;

import static br.com.zbra.androidlinq.Linq.stream;

public final class QueryFactory {
    public static MatchQueryGenerator matchQueryGenerator() {
        return new MatchQueryGenerator();
    }

    public static MultiMatchQueryGenerator multiMatchQueryGenerator() {
        return new MultiMatchQueryGenerator();
    }

    public static BoolQueryGenerator boolQueryGenerator() {
        return new BoolQueryGenerator();
    }

    public static BoostingQueryGenerator boostingQueryGenerator() {
        return new BoostingQueryGenerator();
    }

    public static ConstantScoreQueryGenerator constantScoreQueryGenerator() {
        return new ConstantScoreQueryGenerator();
    }

    public final static class MatchQueryGenerator
            extends QueryGenerator {

        private MatchQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryTypeBag) {
            Map<String, String> childItems = stream(queryTypeBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryTypeBag)
                .firstOrNull(q -> q.isParent());

            return generateParentWithChildren("match", parent, childItems);
        }
    }

    public final static class MultiMatchQueryGenerator
            extends QueryGenerator {

        private MultiMatchQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryTypeBag) {
            Map<String, String> childItems = stream(queryTypeBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("multi_match", childItems);
        }
    }

    public final static class BoolQueryGenerator
            extends QueryGenerator {

        private BoolQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryTypeBag) {
            Map<String, String> childItems = stream(queryTypeBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("bool", childItems);
        }
    }

    public final static class BoostingQueryGenerator
            extends QueryGenerator {

        private BoostingQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryTypeBag) {
            Map<String, String> childItems = stream(queryTypeBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("boosting", childItems);
        }
    }

    public final static class ConstantScoreQueryGenerator
            extends QueryGenerator {

        private ConstantScoreQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryTypeBag) {
            Map<String, String> childItems = stream(queryTypeBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("constant_score", childItems);
        }
    }
}
