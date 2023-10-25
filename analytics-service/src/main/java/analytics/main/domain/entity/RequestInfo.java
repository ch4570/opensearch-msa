package analytics.main.domain.entity;


import analytics.main.domain.dto.RequestLogEvent;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Getter
@ToString
@Table(name = "request_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestInfo {

    @Id
    @Column("request_id")
    private Long id;

    @Column(value = "request_uri")
    private String requestUri;

    @Column(value = "request_method")
    private String requestMethod;

    @Column(value = "request_client_address")
    private String requestClientAddress;

    @Column(value = "content_type")
    private String contentType;

    @Column(value = "user_agent")
    private String userAgent;

    @CreatedDate
    @Column(value = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(value = "updated_at")
    private LocalDateTime updateAt;

    @Builder
    public RequestInfo(String requestUri, String requestMethod, String requestClientAddress, String contentType, String userAgent) {
        this.requestUri = requestUri;
        this.requestMethod = requestMethod;
        this.requestClientAddress = requestClientAddress;
        this.contentType = contentType;
        this.userAgent = userAgent;
    }

    public static RequestInfo createRequestLog(RequestLogEvent requestLog) {
        return RequestInfo.builder()
                .requestUri(requestLog.getRequestUri())
                .requestMethod(requestLog.getRequestMethod())
                .requestClientAddress(requestLog.getRequestClientAddress())
                .contentType(requestLog.getContentType())
                .userAgent(requestLog.getUserAgent())
                .build();
    }
}
