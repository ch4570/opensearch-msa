package com.example.demo.service;

import com.example.demo.domain.dto.response.ApiResponse;
import com.example.demo.domain.dto.response.VMAggregationApiResponse;
import com.example.demo.exception.CustomException;
import static com.example.demo.exception.ErrorCode.*;
import com.example.demo.utils.DomainCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.index.query.BoolQueryBuilder;
import org.opensearch.index.query.ExistsQueryBuilder;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.opensearch.search.sort.FieldSortBuilder;
import org.opensearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;

@Service
@RequiredArgsConstructor
public class VMAggregationApiService implements ApiService {

    private final ObjectMapper mapper;
    private final RestHighLevelClient client;


    @Override
    @SuppressWarnings("unchecked")
    public ApiResponse<VMAggregationApiResponse> searchQuery(ServerRequest request) {

        try {
            // Opensearch 쿼리 생성
            SearchRequest searchRequest = new SearchRequest("sym-metric-vmware-vsphere"); // 인덱스 이름 설정
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            String objectId = request.pathVariable("objectId");
            if (!StringUtils.hasText(objectId)) throw new CustomException(ILLEGAL_OBJECTID_AGGREGATE_BAD_REQUEST);

            // bool 쿼리 및 필터 조건 설정
            BoolQueryBuilder boolQuery = createBoolQuery(objectId);

            searchSourceBuilder.query(boolQuery);
            searchSourceBuilder.size(1);

            // 정렬 설정
            searchSourceBuilder.sort(new FieldSortBuilder("@timestamp").order(SortOrder.DESC));
            searchRequest.source(searchSourceBuilder);

            // OpenSearch Query 결과로 JsonNode 생성
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            JsonNode jsonNode = mapper.readTree(response.toString());

            return new ApiResponse<>(DomainCreator.createVMAggregationApiResponse(jsonNode, mapper));
        } catch (Exception e) {
            throw new CustomException(SEARCH_SERVER_ERROR);
        }

    }

    private BoolQueryBuilder createBoolQuery(String objectId) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery.filter(QueryBuilders.termQuery("object_id.keyword", objectId));
        boolQuery.filter(new ExistsQueryBuilder("basic.vsphere.summary.cluster.count"));
        boolQuery.filter(new ExistsQueryBuilder("basic.vsphere.summary.host.count"));
        boolQuery.filter(new ExistsQueryBuilder("basic.vsphere.summary.vm.count"));
        boolQuery.filter(new ExistsQueryBuilder("basic.vsphere.summary.datastore.count"));

        return boolQuery;
    }
}
