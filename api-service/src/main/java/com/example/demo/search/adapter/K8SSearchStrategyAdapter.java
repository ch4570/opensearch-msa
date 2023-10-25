package com.example.demo.search.adapter;

import com.example.demo.search.strategy.k8s.K8SCpuOptionStrategy;
import com.example.demo.search.strategy.k8s.K8SDiskOptionStrategy;
import com.example.demo.search.strategy.k8s.K8SMemoryOptionStrategy;
import lombok.RequiredArgsConstructor;
import org.opensearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class K8SSearchStrategyAdapter {

    private final K8SDiskOptionStrategy k8SDiskOptionStrategy;
    private final K8SMemoryOptionStrategy k8SMemoryOptionStrategy;
    private final K8SCpuOptionStrategy k8SCpuOptionStrategy;


    public void setK8SDiskOptionStrategy(TermsAggregationBuilder aggregationBuilder, SearchSourceBuilder sourceBuilder) {
        k8SDiskOptionStrategy.setStrategy(aggregationBuilder, sourceBuilder);
    }

    public void setK8SMemoryOptionStrategy(TermsAggregationBuilder aggregationBuilder, SearchSourceBuilder sourceBuilder) {
        k8SMemoryOptionStrategy.setStrategy(aggregationBuilder, sourceBuilder);
    }

    public void setK8SCpuOptionStrategy(TermsAggregationBuilder aggregationBuilder, SearchSourceBuilder sourceBuilder) {
        k8SCpuOptionStrategy.setStrategy(aggregationBuilder, sourceBuilder);
    }
}
