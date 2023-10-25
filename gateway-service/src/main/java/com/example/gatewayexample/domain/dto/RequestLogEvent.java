package com.example.gatewayexample.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class RequestLogEvent implements Serializable {

    private String requestUri;
    private String requestMethod;
    private String requestClientAddress;
    private String contentType;
    private String userAgent;

    public static RequestLogEvent createRequestLog(ServerHttpRequest request) {
        return RequestLogEvent.builder()
                .requestUri(request.getURI().toString())
                .requestMethod(request.getMethod().toString())
                .requestClientAddress(request.getRemoteAddress().toString())
                .contentType(getHeadersByName("Content-Type", request))
                .userAgent(getHeadersByName("User-Agent", request))
                .build();
    }

    private static String getHeadersByName(String headerName, ServerHttpRequest request) {
        List<String> headerList = request.getHeaders().get(headerName);

        return Objects.isNull(headerList) ? "NONE" : headerList.get(0);
    }
}
