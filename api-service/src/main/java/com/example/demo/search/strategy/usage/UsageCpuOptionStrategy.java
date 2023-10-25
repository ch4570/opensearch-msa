package com.example.demo.search.strategy.usage;

import org.opensearch.search.aggregations.AggregationBuilders;
import org.opensearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.opensearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.opensearch.search.aggregations.metrics.MinAggregationBuilder;
import org.springframework.stereotype.Component;

@Component
public class UsageCpuOptionStrategy implements UsageApiSearchStrategy {
    @Override
    public void setMaxStrategy(DateHistogramAggregationBuilder dataHistogramAgg) {
        MaxAggregationBuilder maxCpuAgg = AggregationBuilders.max("vm_hypervisor_status_cpu_utillization")
                .field("basic.host.cpu.usage.norm.pct");
        dataHistogramAgg.subAggregation(maxCpuAgg);
    }

    @Override
    public void setMinStrategy(DateHistogramAggregationBuilder dataHistogramAgg) {
        MinAggregationBuilder minCpuAgg = AggregationBuilders.min("vm_hypervisor_status_cpu_utillization")
                .field("basic.host.cpu.usage.norm.pct");
        dataHistogramAgg.subAggregation(minCpuAgg);
    }
}
