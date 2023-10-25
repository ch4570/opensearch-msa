package com.example.demo.router;

import com.example.demo.handler.TopNHandler;
import com.example.demo.handler.UsageHandler;
import com.example.demo.handler.VMAggregationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(TopNHandler topNHandler,
                                                 UsageHandler usageHandler,
                                                 VMAggregationHandler vmAggregationHandler) {
        return RouterFunctions.route()
                .GET("/api/usage/{hostId}", usageHandler::callApi)
                .GET("/api/topN", topNHandler::callApi)
                .GET("api/vm/aggregate/{objectId}", vmAggregationHandler::callApi)
                .build();
    }
}
