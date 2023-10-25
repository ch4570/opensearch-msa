package com.example.demo.search.strategy.usage;

import org.opensearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;

public interface UsageApiSearchStrategy {

    void setMaxStrategy(DateHistogramAggregationBuilder dataHistogramAgg);

    void setMinStrategy(DateHistogramAggregationBuilder dataHistogramAgg);
}
