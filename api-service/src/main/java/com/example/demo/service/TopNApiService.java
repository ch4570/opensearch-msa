package com.example.demo.service;

import com.example.demo.domain.dto.request.TopNSearchCondition;
import com.example.demo.domain.dto.response.ApiResponse;
import com.example.demo.domain.dto.response.TopNApiResponse;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.search.adapter.K8SSearchStrategyAdapter;
import com.example.demo.utils.DomainCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.search.aggregations.AggregationBuilders;
import org.opensearch.search.aggregations.BucketOrder;
import org.opensearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopNApiService implements ApiService{

    private final RestHighLevelClient client;
    private final ObjectMapper mapper;
    private final K8SSearchStrategyAdapter strategyAdapter;


    @Override
    @SuppressWarnings("unchecked")
    public ApiResponse<TopNApiResponse> searchQuery(ServerRequest request) {
        TopNSearchCondition searchCondition = createSearchCondition(request);

        try {

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.size(0);

            TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("top_n")
                    .field("object_id.keyword")
                    .size(searchCondition.getSize())
                    .order(BucketOrder.aggregation("usage", searchCondition.isSort()));

            sourceBuilder.aggregation(aggregationBuilder);

            setAggregateAndBoolQuery(aggregationBuilder, sourceBuilder, searchCondition.getOption());

            SearchRequest searchRequest = new SearchRequest("sym-metric-vmware-*");
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            JsonNode jsonNode = mapper.readTree(searchResponse.toString());

            return new ApiResponse<>(DomainCreator.createTopNApiResponse(jsonNode, mapper, searchCondition.getOption()));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.SEARCH_SERVER_ERROR);
        }
    }


    private TopNSearchCondition createSearchCondition(ServerRequest request) {
        return new TopNSearchCondition.TopNSearchConditionBuilder()
                .option(request.queryParam("option").orElse("cpu"))
                .sort(request.queryParam("sort").orElse("desc"))
                .size(request.queryParam("size").orElse("10"))
                .build();
    }
    
    private void setAggregateAndBoolQuery(TermsAggregationBuilder aggregationBuilder, SearchSourceBuilder sourceBuilder, String option) {

        switch (option) {
            case "disk" -> strategyAdapter.setK8SDiskOptionStrategy(aggregationBuilder, sourceBuilder);
            case "memory" -> strategyAdapter.setK8SMemoryOptionStrategy(aggregationBuilder, sourceBuilder);
            case "cpu" -> strategyAdapter.setK8SCpuOptionStrategy(aggregationBuilder, sourceBuilder);
        }
    }
}
