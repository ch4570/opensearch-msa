package com.example.gatewayexample.event;

import com.example.gatewayexample.domain.dto.RequestLogEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestLogEventHandler {

    private final KafkaTemplate<String, RequestLogEvent> kafkaTemplate;

    @Async
    @EventListener
    public void sendRequestLog(RequestLogEvent requestLogEvent) {
        System.out.println("requestLogEvent = " + requestLogEvent);
        kafkaTemplate.send("request_log", requestLogEvent);
    }
}
