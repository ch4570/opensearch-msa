package com.example.gatewayexample.filter;

import com.example.gatewayexample.domain.dto.RequestLogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {


    private final ApplicationEventPublisher eventPublisher;

    public LoggingFilter(ApplicationEventPublisher eventPublisher) {
        super(Config.class);
        this.eventPublisher = eventPublisher;

    }


    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            RequestLogEvent requestLog = RequestLogEvent.createRequestLog(exchange.getRequest());
            eventPublisher.publishEvent(requestLog);
        })));
    }


    static class Config {}

}
