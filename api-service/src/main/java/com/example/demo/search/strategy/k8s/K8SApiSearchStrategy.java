package com.example.demo.search.strategy.k8s;

import org.opensearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.opensearch.search.builder.SearchSourceBuilder;

public interface K8SApiSearchStrategy {

    void setStrategy(TermsAggregationBuilder aggregationBuilder, SearchSourceBuilder sourceBuilder);
}
