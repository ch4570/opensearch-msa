package com.example.demo.service;

import com.example.demo.domain.dto.response.ApiResponse;
import com.example.demo.domain.dto.response.UsageApiResponse;
import com.example.demo.domain.dto.request.UsageSearchCondition;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.search.adapter.UsageSearchStrategyAdapter;
import com.example.demo.utils.DomainCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.index.query.BoolQueryBuilder;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.aggregations.AggregationBuilders;
import org.opensearch.search.aggregations.BucketOrder;
import org.opensearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.opensearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.opensearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;


@Slf4j
@Service
@RequiredArgsConstructor
public class UsageApiService implements ApiService {

    private final ObjectMapper mapper;
    private final RestHighLevelClient restHighLevelClient;
    private final UsageSearchStrategyAdapter strategyAdapter;

    @Override
    @SuppressWarnings("unchecked")
    public ApiResponse<UsageApiResponse> searchQuery(ServerRequest request) {


        UsageSearchCondition searchCondition = createSearchCondition(request);

        try {

            // 검색 요청 생성
            SearchRequest searchRequest = new SearchRequest("sym-metric-vmware-*"); // 색인 이름 지정
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();


            BoolQueryBuilder boolQuery = createdBoolQuery(searchCondition.getFrom(), searchCondition.getHostId());
            sourceBuilder.query(boolQuery);
            sourceBuilder.size(0); // 결과 크기

            // Aggregation 설정
            TermsAggregationBuilder termsAgg = createTermsAggregationBuilder();

            // DataHistogram으로 주어진 범위 내에서 시간 별 데이터를 추적
            DateHistogramAggregationBuilder dateHistogramAgg = createDateHistogramBuilder(searchCondition.getInterval());

            // SubAggregation 셋팅
            setSubAggregation(searchCondition.getType(), searchCondition.getOption(), dateHistogramAgg);
            termsAgg.subAggregation(dateHistogramAgg);
            sourceBuilder.aggregation(termsAgg);
            searchRequest.source(sourceBuilder);

            // 검색 요청 실행
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            JsonNode jsonNode = mapper.readTree(response.toString());

            return new ApiResponse<>(DomainCreator.createUsageApiResponse(jsonNode, mapper));

        } catch (Exception e) {
            throw new CustomException(ErrorCode.SEARCH_SERVER_ERROR);
        }
    }

    private BoolQueryBuilder createdBoolQuery(int from, String hostId) {
        // 쿼리 구성
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 필드가 존재할때만 쿼리 대상이 되도록 filter 처리
        boolQuery.filter(QueryBuilders.existsQuery("basic.host.memory.usage"));
        boolQuery.filter(QueryBuilders.existsQuery("basic.host.cpu.usage.norm.pct"));

        // Filter 조건 추가 -> from ~ to 시간 filter 처리
        boolQuery.filter(QueryBuilders.rangeQuery("@timestamp")
                .from("now-"+ from +"h")
                .to("now")
        );

        boolQuery.filter(QueryBuilders.termsQuery("object_id.keyword", hostId));

        return boolQuery;
    }

    private TermsAggregationBuilder createTermsAggregationBuilder() {
        return AggregationBuilders.terms("group_aggs")
                .field("object_id.keyword")
                .size(1000)
                .order(BucketOrder.key(true));
    }

    private DateHistogramAggregationBuilder createDateHistogramBuilder(int interval) {
        return AggregationBuilders.dateHistogram("date_histogram")
                .field("@timestamp")
                .fixedInterval(DateHistogramInterval.minutes(interval))
                .order(BucketOrder.key(true));
    }


    private static UsageSearchCondition createSearchCondition(ServerRequest request) {
        return new UsageSearchCondition.UsageConditionBuilder()
                .hostId(request.pathVariable("hostId"))
                .from(request.queryParam("from").orElse("3"))
                .type(request.queryParam("type").orElse("max"))
                .interval(request.queryParam("interval").orElse("30"))
                .option(request.queryParam("option").orElse("all"))
                .build();
    }

    private void setSubAggregation(String type, String option, DateHistogramAggregationBuilder dataHistogramAgg) {

        switch (type) {
            case "max" -> setMaxAggregationType(dataHistogramAgg, option);
            case "min" -> setMinAggregationType(dataHistogramAgg, option);
        }
    }


    private void setMaxAggregationType(DateHistogramAggregationBuilder dataHistogramAgg, String option) {


        switch (option) {
            case "ram" -> strategyAdapter.setUsageRamMaxOptionStrategy(dataHistogramAgg);
            case "cpu" -> strategyAdapter.setUsageCpuMaxOptionStrategy(dataHistogramAgg);
            case "all" -> strategyAdapter.setUsageAllMaxOptionStrategy(dataHistogramAgg);
        }
    }

    private void setMinAggregationType(DateHistogramAggregationBuilder dataHistogramAgg, String option) {


        switch (option) {
            case "ram" -> strategyAdapter.setUsageRamMinOptionStrategy(dataHistogramAgg);
            case "cpu" -> strategyAdapter.setUsageCpuMinOptionStrategy(dataHistogramAgg);
            case "all" -> strategyAdapter.setUsageAllMinOptionStrategy(dataHistogramAgg);
        }
    }





}



