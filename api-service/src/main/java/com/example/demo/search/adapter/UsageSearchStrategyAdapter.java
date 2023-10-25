package com.example.demo.search.adapter;

import com.example.demo.search.strategy.usage.UsageAllOptionStrategy;
import com.example.demo.search.strategy.usage.UsageCpuOptionStrategy;
import com.example.demo.search.strategy.usage.UsageRamOptionStrategy;
import lombok.RequiredArgsConstructor;
import org.opensearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsageSearchStrategyAdapter {

    private final UsageAllOptionStrategy usageAllOptionStrategy;
    private final UsageCpuOptionStrategy usageCpuOptionStrategy;
    private final UsageRamOptionStrategy usageRamOptionStrategy;


    public void setUsageAllMaxOptionStrategy(DateHistogramAggregationBuilder dataHistogramAgg) {
        usageAllOptionStrategy.setMaxStrategy(dataHistogramAgg);
    }

    public void setUsageAllMinOptionStrategy(DateHistogramAggregationBuilder dataHistogramAgg) {
        usageAllOptionStrategy.setMinStrategy(dataHistogramAgg);
    }

    public void setUsageCpuMaxOptionStrategy(DateHistogramAggregationBuilder dataHistogramAgg) {
        usageCpuOptionStrategy.setMaxStrategy(dataHistogramAgg);
    }

    public void setUsageCpuMinOptionStrategy(DateHistogramAggregationBuilder dataHistogramAgg) {
        usageCpuOptionStrategy.setMinStrategy(dataHistogramAgg);
    }

    public void setUsageRamMaxOptionStrategy(DateHistogramAggregationBuilder dataHistogramAgg) {
        usageRamOptionStrategy.setMaxStrategy(dataHistogramAgg);
    }

    public void setUsageRamMinOptionStrategy(DateHistogramAggregationBuilder dataHistogramAgg) {
        usageRamOptionStrategy.setMinStrategy(dataHistogramAgg);
    }
}
