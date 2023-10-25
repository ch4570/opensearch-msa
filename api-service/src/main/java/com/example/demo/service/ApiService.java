package com.example.demo.service;

import com.example.demo.domain.dto.response.ApiResponse;
import org.springframework.web.reactive.function.server.ServerRequest;

public interface ApiService {

    <T> ApiResponse<T> searchQuery(ServerRequest request);
}
