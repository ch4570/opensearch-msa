package analytics.main.domain.dto;


import lombok.Getter;

import java.io.Serializable;

@Getter
public class RequestLogEvent implements Serializable {

    private String requestUri;
    private String requestMethod;
    private String requestClientAddress;
    private String contentType;
    private String userAgent;

}
