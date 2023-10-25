package com.example.demo.handler;

import com.example.demo.domain.dto.response.ApiResponse;
import com.example.demo.domain.dto.response.VMAggregationApiResponse;
import com.example.demo.service.ApiService;
import com.example.demo.service.VMAggregationApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class VMAggregationHandler implements ApiHandler {

    private final ApiService apiService;

    @Autowired
    public VMAggregationHandler(VMAggregationApiService apiService) {
        this.apiService = apiService;
    }

    /*
    *   Vsphere 의 ObjectId를 지정하여 특정 ObjectId의
    *   클러스터 수, VM의 수, 작동중인 VM의 수, Datastore의 수, Host의 수를 보여주는 집계 API
    * */
    public Mono<ServerResponse> callApi(ServerRequest request) {
        ApiResponse<VMAggregationApiResponse> response = apiService.searchQuery(request);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(response), ApiResponse.class);
    }


}