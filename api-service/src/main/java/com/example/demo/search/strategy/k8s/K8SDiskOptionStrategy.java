package com.example.demo.search.strategy.k8s;

import org.opensearch.index.query.BoolQueryBuilder;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.aggregations.AggregationBuilders;
import org.opensearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.opensearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

@Component
public class K8SDiskOptionStrategy implements K8SApiSearchStrategy{
    @Override
    public void setStrategy(TermsAggregationBuilder aggregationBuilder, SearchSourceBuilder sourceBuilder) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(QueryBuilders.existsQuery("basic.k8s.node.disk.usage.pct"));
        MaxAggregationBuilder maxAggregationBuilder = AggregationBuilders.max("usage")
                .field("basic.k8s.node.disk.usage.pct");
        sourceBuilder.query(boolQueryBuilder);
        aggregationBuilder.subAggregation(maxAggregationBuilder);
    }
}
