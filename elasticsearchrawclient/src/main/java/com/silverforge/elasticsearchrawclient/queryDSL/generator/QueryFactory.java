package com.silverforge.elasticsearchrawclient.queryDSL.generator;

import com.silverforge.elasticsearchrawclient.model.QueryTypeItem;
import com.silverforge.elasticsearchrawclient.utils.QueryTypeArrayList;

import java.util.Map;

import static br.com.zbra.androidlinq.Linq.stream;

public final class QueryFactory {

    public static QueryGenerator queryGenerator() {
        return new QueryGenerator();
    }

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

    public static IdsQueryGenerator idsQueryGenerator() {
        return new IdsQueryGenerator();
    }

    public static ScriptGenerator scriptGenerator() {
        return new ScriptGenerator();
    }

    public static FuzzyQueryGenerator fuzzyQueryGenerator() {
        return new FuzzyQueryGenerator();
    }

    public static GeoShapeQueryGenerator geoShapeQueryGenerator() {
        return new GeoShapeQueryGenerator();
    }

    public static GeoBoundingBoxQueryGenerator geoBoundingBoxQueryGenerator() {
        return new GeoBoundingBoxQueryGenerator();
    }

    public static GeoDistanceQueryGenerator geoDistanceQueryGenerator() {
        return new GeoDistanceQueryGenerator();
    }

    public static GeoDistanceRangeQueryGenerator geoDistanceRangeQueryGenerator() {
        return new GeoDistanceRangeQueryGenerator();
    }

    public static GeoPolygonQueryGenerator geoPolygonQueryGenerator() {
        return new GeoPolygonQueryGenerator();
    }

    public static GeohashCellQueryGenerator geohashCellQueryGenerator() {
        return new GeohashCellQueryGenerator();
    }

    public static IndicesQueryGenerator indicesQueryGenerator() {
        return new IndicesQueryGenerator();
    }

    public static MatchAllQueryGenerator matchAllQueryGenerator() {
        return new MatchAllQueryGenerator();
    }

    public static MoreLikeThisQueryGenerator moreLikeThisQueryGenerator() {
        return new MoreLikeThisQueryGenerator();
    }

    public static NestedQueryGenerator nestedQueryGenerator() {
        return new NestedQueryGenerator();
    }

    public static PrefixQueryGenerator prefixQueryGenerator() {
        return new PrefixQueryGenerator();
    }

    public static RangeQueryGenerator rangeQueryGenerator() {
        return new RangeQueryGenerator();
    }

    public static RegexpQueryGenerator regexpQueryGenerator() {
        return new RegexpQueryGenerator();
    }

    public static SimpleQueryStringQueryGenerator simpleQueryStringQueryGenerator() {
        return new SimpleQueryStringQueryGenerator();
    }

    public static SpanFirstQueryGenerator spanFirstQueryGenerator() {
        return new SpanFirstQueryGenerator();
    }

    public static QueryStringQueryGenerator queryStringQueryGenerator() {
        return new QueryStringQueryGenerator();
    }

    public static WildCardQueryGenerator wildCardQueryGenerator() {
        return new WildCardQueryGenerator();
    }

    public static ScriptQueryGenerator scriptQueryGenerator() {
        return new ScriptQueryGenerator();
    }

    public static TemplateQueryGenerator templateQueryGenerator() {
        return new TemplateQueryGenerator();
    }

    public static ExistsQueryGenerator existsQueryGenerator() {
        return new ExistsQueryGenerator();
    }

    public static MissingQueryGenerator missingQueryGenerator() {
        return new MissingQueryGenerator();
    }

    public static TypeQueryGenerator typeQueryGenerator() {
        return new TypeQueryGenerator();
    }

    public static TermsQueryGenerator termsQueryGenerator() {
        return new TermsQueryGenerator();
    }

    public static TermQueryGenerator termQueryGenerator() {
        return new TermQueryGenerator();
    }

    public static NotQueryGenerator notQueryGenerator() {
        return new NotQueryGenerator();
    }

    public static LimitQueryGenerator limitQueryGenerator() {
        return new LimitQueryGenerator();
    }

    public static SpanTermQueryGenerator spanTermQueryGenerator() {
        return new SpanTermQueryGenerator();
    }

    public static SpanNearQueryGenerator spanNearQueryGenerator() {
        return new SpanNearQueryGenerator();
    }

    public static SpanOrQueryGenerator spanOrQueryGenerator() {
        return new SpanOrQueryGenerator();
    }

    public static SpanNotQueryGenerator spanNotQueryGenerator() {
        return new SpanNotQueryGenerator();
    }

    public static SpanContainingQueryGenerator spanContainingQueryGenerator() {
        return new SpanContainingQueryGenerator();
    }

    public static SpanWithinQueryGenerator spanWithinQueryGenerator() {
        return new SpanWithinQueryGenerator();
    }

    public static SpanMultiTermQueryGenerator spanMultiTermQueryGenerator() {
        return new SpanMultiTermQueryGenerator();
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

    public final static class IdsQueryGenerator
            extends QueryGenerator {

        private IdsQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                .firstOrNull(q -> q.isParent());

            return generateCommonChildren("ids", parent, childItems);
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

    public final static class GeoShapeQueryGenerator
            extends QueryGenerator {

        private GeoShapeQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                .firstOrNull(q -> q.isParent());

            return generateGeoShapeChildren("geo_shape", parent, childItems);
        }
    }

    public final static class GeoBoundingBoxQueryGenerator
            extends QueryGenerator {

        private GeoBoundingBoxQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                .firstOrNull(q -> q.isParent());

            return generateParentChildren("geo_bounding_box", parent, childItems);
        }
    }

    public final static class GeoDistanceQueryGenerator
            extends QueryGenerator {

        private GeoDistanceQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                .firstOrNull(q -> q.isParent());

            return generateGeoDistance("geo_distance", parent, childItems);
        }
    }

    public final static class GeoDistanceRangeQueryGenerator
            extends QueryGenerator {

        private GeoDistanceRangeQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                .firstOrNull(q -> q.isParent());

            return generateGeoDistance("geo_distance_range", parent, childItems);
        }
    }

    public final static class GeoPolygonQueryGenerator
            extends QueryGenerator {

        private GeoPolygonQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                .firstOrNull(q -> q.isParent());

            return generateGeoPolygon("geo_polygon", parent, childItems);
        }
    }

    public final static class GeohashCellQueryGenerator
            extends QueryGenerator {

        private GeohashCellQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                .firstOrNull(q -> q.isParent());

            return generateGeoDistance("geohash_cell", parent, childItems);
        }
    }

    public final static class IndicesQueryGenerator
            extends QueryGenerator {

        private IndicesQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("indices", childItems);
        }
    }

    public final static class MatchAllQueryGenerator
            extends QueryGenerator {

        private MatchAllQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("match_all", childItems);
        }
    }

    public final static class MoreLikeThisQueryGenerator
            extends QueryGenerator {

        private MoreLikeThisQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("more_like_this", childItems);
        }
    }

    public final static class NestedQueryGenerator
            extends QueryGenerator {

        private NestedQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("nested", childItems);
        }
    }

    public final static class PrefixQueryGenerator
            extends QueryGenerator {

        private PrefixQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                .firstOrNull(q -> q.isParent());

            return generateCommonChildren("prefix", parent, childItems);
        }
    }

    public final static class RangeQueryGenerator
            extends QueryGenerator {

        private RangeQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                .firstOrNull(q -> q.isParent());

            return generateFuzzyChildren("range", parent, childItems);
        }
    }

    public final static class RegexpQueryGenerator
            extends QueryGenerator {

        private RegexpQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                .firstOrNull(q -> q.isParent());

            return generateFuzzyChildren("regexp", parent, childItems);
        }
    }

    public final static class SimpleQueryStringQueryGenerator
        extends QueryGenerator {

        private SimpleQueryStringQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("simple_query_string", childItems);
        }
    }

    public final static class QueryStringQueryGenerator
            extends QueryGenerator {

        private QueryStringQueryGenerator() {}

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("query_string", childItems);
        }
    }

    public final static class WildCardQueryGenerator
            extends QueryGenerator {

        private WildCardQueryGenerator() {}

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .where(q -> !q.isParent())
                .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                .firstOrNull(q -> q.isParent());

            return generateFuzzyChildren("wildcard", parent, childItems);
        }
    }

    public final static class SpanFirstQueryGenerator
            extends QueryGenerator {

        private SpanFirstQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                .toMap(q -> q.getName(), q -> q.getValue());

            return generateSpanFirst("span_first", childItems);
        }
    }

    public final static class ScriptQueryGenerator
            extends QueryGenerator {

        private ScriptQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("script", childItems);
        }
    }

    public final static class TemplateQueryGenerator
            extends QueryGenerator {

        private TemplateQueryGenerator() {}

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("template", childItems);
        }
    }

    public final static class ExistsQueryGenerator
            extends QueryGenerator {

        private ExistsQueryGenerator() {}

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("exists", childItems);
        }
    }

    public final static class MissingQueryGenerator
            extends QueryGenerator {

        private MissingQueryGenerator() {}

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("missing", childItems);
        }
    }

    public final static class TypeQueryGenerator
            extends QueryGenerator {

        private TypeQueryGenerator() {}

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("type", childItems);
        }
    }

    public final static class TermsQueryGenerator
            extends QueryGenerator {

        private TermsQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("terms", childItems);
        }
    }

    public final static class TermQueryGenerator
            extends QueryGenerator {

        private TermQueryGenerator() {}

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .where(q -> !q.isParent())
                    .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                    .firstOrNull(q -> q.isParent());

            return generateFuzzyChildren("term", parent, childItems);
        }
    }

    public final static class NotQueryGenerator
            extends QueryGenerator {

        private NotQueryGenerator() {}

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("not", childItems);
        }
    }

    public final static class LimitQueryGenerator
            extends QueryGenerator {

        private LimitQueryGenerator() {}

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("limit", childItems);
        }
    }

    public final static class SpanTermQueryGenerator
            extends QueryGenerator {

        private SpanTermQueryGenerator() {}

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .where(q -> !q.isParent())
                    .toMap(q -> q.getName(), q -> q.getValue());

            QueryTypeItem parent = stream(queryBag)
                    .firstOrNull(q -> q.isParent());

            return generateFuzzyChildren("span_term", parent, childItems);
        }
    }

    public final static class SpanNearQueryGenerator
            extends QueryGenerator {

        private SpanNearQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("span_near", childItems);
        }
    }

    public final static class SpanOrQueryGenerator
            extends QueryGenerator {

        private SpanOrQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("span_or", childItems);
        }
    }

    public final static class SpanNotQueryGenerator
            extends QueryGenerator {

        private SpanNotQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("span_not", childItems);
        }
    }

    public final static class SpanContainingQueryGenerator
            extends QueryGenerator {

        private SpanContainingQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("span_containing", childItems);
        }
    }

    public final static class SpanWithinQueryGenerator
            extends QueryGenerator {

        private SpanWithinQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("span_within", childItems);
        }
    }

    public final static class SpanMultiTermQueryGenerator
            extends QueryGenerator {

        private SpanMultiTermQueryGenerator() {
        }

        @Override
        public String generate(QueryTypeArrayList<QueryTypeItem> queryBag) {
            Map<String, String> childItems = stream(queryBag)
                    .toMap(q -> q.getName(), q -> q.getValue());

            return generateChildren("span_multi", childItems);
        }
    }
}

