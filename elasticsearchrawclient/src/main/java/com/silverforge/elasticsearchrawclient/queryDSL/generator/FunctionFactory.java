package com.silverforge.elasticsearchrawclient.queryDSL.generator;

import com.silverforge.elasticsearchrawclient.model.QueryTypeItem;
import com.silverforge.elasticsearchrawclient.utils.QueryTypeArrayList;

import java.util.Map;

import static br.com.zbra.androidlinq.Linq.stream;

public final class FunctionFactory {

    public static WeightGenerator weightGenerator() {
        return new WeightGenerator();
    }

    public static ScriptScoreGenerator scriptScoreGenerator() {
        return new ScriptScoreGenerator();
    }

    public static RandomScoreGenerator randomScoreGenerator() {
        return new RandomScoreGenerator();
    }

    public static class WeightGenerator
            extends QueryGenerator {

        private WeightGenerator() {}

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateFunctionChildren(childItems);
        }
    }

    public static class ScriptScoreGenerator
            extends QueryGenerator {

        private ScriptScoreGenerator() {}

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateScriptScoreChildren("script_score", childItems);
        }
    }

    public static class RandomScoreGenerator
            extends QueryGenerator {

        private RandomScoreGenerator() {}

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateFunctionChildren("random_score", childItems);
        }
    }
}
