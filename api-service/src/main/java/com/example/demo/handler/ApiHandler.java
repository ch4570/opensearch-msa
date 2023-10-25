package com.example.demo.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface ApiHandler {

    Mono<ServerResponse> callApi(ServerRequest request);
}
