package com.example.demo.utils;


import com.example.demo.domain.dto.response.TopNApiResponse;
import com.example.demo.domain.dto.response.UsageApiResponse;
import com.example.demo.domain.dto.response.VMAggregationApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

/*
*  JSON Parsing Cost를 줄이기 위해서 Json Node를 직접 타고 내려가,
*  필요한 데이터만 도메인 객체에 넣어서 반환하는 클래스
* */
public class DomainCreator {


    public static UsageApiResponse createUsageApiResponse(JsonNode jsonNode, ObjectMapper mapper) {

        // OpenSearch 쿼리의 결과를 JsonNode에 넣어서 원하는 프로퍼티까지 접근한다.
        String hostId = jsonNode.get("aggregations").get("sterms#group_aggs").get("buckets").get(0).get("key").asText();

        // 원하는 프로퍼티에 접근해서 원하는 데이터를 Java 객체로 변환해준다.
        List<UsageApiResponse.Bucket> buckets = mapper.convertValue(jsonNode.get("aggregations")
                        .get("sterms#group_aggs")
                        .get("buckets")
                        .get(0)
                        .get("date_histogram#date_histogram")
                        .get("buckets"),
                new TypeReference<>() {});

        List<UsageApiResponse.UsageResponse> responses = buckets.stream()
                .map(UsageApiResponse.UsageResponse::new)
                .collect(Collectors.toList());

        return new UsageApiResponse(hostId, responses);
    }


    public static TopNApiResponse createTopNApiResponse(JsonNode jsonNode, ObjectMapper mapper, String option) {

        List<TopNApiResponse.Bucket> buckets = mapper.convertValue(jsonNode.get("aggregations")
                .get("sterms#top_n")
                .get("buckets"), new TypeReference<>(){});

        List<TopNApiResponse.TopNResponse> responses = buckets.stream()
                .map(TopNApiResponse.TopNResponse::new)
                .collect(Collectors.toList());

        return new TopNApiResponse(option, responses);
    }

    public static VMAggregationApiResponse createVMAggregationApiResponse(JsonNode jsonNode, ObjectMapper mapper) {
        return mapper.convertValue(jsonNode.get("hits")
                .get("hits")
                .get(0)
                .get("_source")
                .get("basic")
                .get("vsphere")
                .get("summary"),
                VMAggregationApiResponse.class);
    }
}
