package com.example.demo.exception;


import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {


    /*
    *  Custom 예외를 처리할 때 사용자에게 보여줄 메세지를 정의
    *  -> 기존 예외 메시지는 사용자에게 유용한 정보를 주기 어려워, CustomAttributes 정의
    * */
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

        Map<String, Object> errorMap = super.getErrorAttributes(request, options);

        Throwable throwable = getError(request);

        if (throwable instanceof CustomException) {
            CustomException ex = (CustomException) getError(request);
            return Map.of("status", ex.getErrorCode().getHttpStatus().value(),
                         "message", ex.getErrorCode().getMessage(),
                        "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")),
                        "requestPath", request.requestPath().toString()
                    );
        }

        return errorMap;
    }
}
