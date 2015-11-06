package com.silverforge.elasticsearchrawclient.queryDSL.generator;

import com.silverforge.elasticsearchrawclient.model.QueryTypeItem;
import com.silverforge.elasticsearchrawclient.queryDSL.fieldValueFactors.FieldValueFactor;
import com.silverforge.elasticsearchrawclient.queryDSL.queries.innerQueries.HasChildQuery;
import com.silverforge.elasticsearchrawclient.queryDSL.queries.innerQueries.HasParentQuery;
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

    public static DisMaxQueryGenerator disMaxQueryGenerator() {
        return new DisMaxQueryGenerator();
    }

    public static FunctionScoreQueryGenerator functionScoreQueryGenerator() {
        return new FunctionScoreQueryGenerator();
    }

    public static CommonTermsQueryGenerator commonTermsQueryGenerator() {
        return new CommonTermsQueryGenerator();
    }

    public static HasChildQueryGenerator hasChildQueryGenerator() {
        return new HasChildQueryGenerator();
    }

    public static HasParentQueryGenerator hasParentQueryGenerator() {
        return new HasParentQueryGenerator();
    }

    public static FunctionGenerator functionGenerator() {
        return new FunctionGenerator();
    }

    public static ScriptGenerator scriptGenerator() {
        return new ScriptGenerator();
    }

    public static FieldValueFactorGenerator fieldValueFactorGenerator() {
        return new FieldValueFactorGenerator();
    }

    public static FuzzyQueryGenerator fuzzyQueryGenerator() {
        return new FuzzyQueryGenerator();
    }

    public final static class MatchQueryGenerator
            extends QueryGenerator {

        private MatchQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                .firstOrNull(q -> q.isParent());

            return generateMatchChildren("match", parent, childItems);
        }
    }

    public final static class MultiMatchQueryGenerator
            extends QueryGenerator {

        private MultiMatchQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("multi_match", childItems);
        }
    }

    public final static class BoolQueryGenerator
            extends QueryGenerator {

        private BoolQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("bool", childItems);
        }
    }

    public final static class BoostingQueryGenerator
            extends QueryGenerator {

        private BoostingQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("boosting", childItems);
        }
    }

    public final static class ConstantScoreQueryGenerator
            extends QueryGenerator {

        private ConstantScoreQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("constant_score", childItems);
        }
    }

    public final static class DisMaxQueryGenerator
            extends QueryGenerator {

        private DisMaxQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("dis_max", childItems);
        }
    }

    public final static class FunctionScoreQueryGenerator
            extends QueryGenerator {

        private FunctionScoreQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("function_score", childItems);
        }
    }

    public final static class CommonTermsQueryGenerator
            extends QueryGenerator {

        private CommonTermsQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .where(q -> !q.isParent())
                    .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                    .firstOrNull(q -> q.isParent());

            return generateCommonChildren("common", parent, childItems);
        }
    }

    public final static class HasChildQueryGenerator
            extends QueryGenerator {

        private HasChildQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .where(q -> !q.isParent())
                    .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                    .firstOrNull(q -> q.isParent());

            return generateCommonChildren("has_child", parent, childItems);
        }
    }

    public final static class HasParentQueryGenerator
            extends QueryGenerator {

        private HasParentQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .where(q -> !q.isParent())
                    .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                    .firstOrNull(q -> q.isParent());

            return generateCommonChildren("has_parent", parent, childItems);
        }
    }

    public final static class FunctionGenerator
            extends QueryGenerator {

        private FunctionGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren(childItems);
        }
    }

    public final static class ScriptGenerator
            extends QueryGenerator {

        private ScriptGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren(childItems);
        }
    }

    public final static class FieldValueFactorGenerator
            extends QueryGenerator {

        private FieldValueFactorGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren(childItems);
        }
    }

    public final static class FuzzyQueryGenerator
        extends QueryGenerator {

        private FuzzyQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                .firstOrNull(q -> q.isParent());

            return generateFuzzyChildren("fuzzy", parent, childItems);
        }
    }
}
