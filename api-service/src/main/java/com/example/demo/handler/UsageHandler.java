package com.example.demo.handler;

import com.example.demo.domain.dto.response.ApiResponse;
import com.example.demo.domain.dto.response.UsageApiResponse;
import com.example.demo.service.ApiService;
import com.example.demo.service.UsageApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UsageHandler implements ApiHandler {

    private final ApiService apiService;

    @Autowired
    public UsageHandler(UsageApiService apiService) {
        this.apiService = apiService;
    }

    /*
    *   Route 방식에 필요한 핸들러 정의
    *   Host-ID를 지정하여 특정 Interval(분 단위) 단위로 현재부터 From(시간 단위) 까지
    *   Memory, Cpu 사용량을 최소 또는 최대 값으로 볼 수 있는 API
    * */
    public Mono<ServerResponse> callApi(ServerRequest request) {
        ApiResponse<UsageApiResponse> response = apiService.searchQuery(request);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(response), ApiResponse.class);
    }



}