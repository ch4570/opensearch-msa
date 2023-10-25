package com.example.demo.handler;

import com.example.demo.domain.dto.response.ApiResponse;
import com.example.demo.domain.dto.response.TopNApiResponse;
import com.example.demo.service.ApiService;
import com.example.demo.service.TopNApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TopNHandler implements ApiHandler {

    private final ApiService apiService;

    @Autowired
    public TopNHandler(TopNApiService apiService) {
        this.apiService = apiService;
    }


    /*
    *  K8S Node들의 Memory, Cpu, Disk 사용 량 TopN을 보여주는 API
    *  정렬 기준에 따라 최소 값을 보여주는 BottomN도 가능
    * */
    @Override
    public Mono<ServerResponse> callApi(ServerRequest request) {
        ApiResponse<TopNApiResponse> response = apiService.searchQuery(request);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(response), ApiResponse.class);
    }
}