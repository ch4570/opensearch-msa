package com.example.demo.search.strategy.usage;

import org.opensearch.search.aggregations.AggregationBuilders;
import org.opensearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.opensearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.opensearch.search.aggregations.metrics.MinAggregationBuilder;
import org.springframework.stereotype.Component;

@Component
public class UsageRamOptionStrategy implements UsageApiSearchStrategy {
    @Override
    public void setMaxStrategy(DateHistogramAggregationBuilder dataHistogramAgg) {
        MaxAggregationBuilder maxRamAgg = AggregationBuilders.max("vm_hypervisor_status_memory_utillization")
                .field("basic.host.memory.usage");
        dataHistogramAgg.subAggregation(maxRamAgg);
    }

    @Override
    public void setMinStrategy(DateHistogramAggregationBuilder dataHistogramAgg) {
        MinAggregationBuilder minRamAgg = AggregationBuilders.min("vm_hypervisor_status_memory_utillization")
                .field("basic.host.memory.usage");
        dataHistogramAgg.subAggregation(minRamAgg);
    }
}
