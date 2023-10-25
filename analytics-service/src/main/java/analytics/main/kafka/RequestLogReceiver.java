package analytics.main.kafka;

import analytics.main.domain.dto.RequestLogEvent;
import analytics.main.domain.entity.RequestInfo;
import analytics.main.service.RequestInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestLogReceiver {

    private final RequestInfoService requestInfoService;

    @KafkaListener(topics = "request_log", groupId = "request_log")
    public void receiveRequestLog(RequestLogEvent requestLog) {
        log.info("message 도착 !");
        RequestInfo requestInfo = RequestInfo.createRequestLog(requestLog);
        requestInfoService.saveRequestLog(requestInfo);
        log.info("message 저장 완료!");
    }
}
