package com.example.demo.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class TopNApiResponse {

    private String type;
    private List<TopNResponse> result;



    @Getter
    @ToString
    public static class Bucket {

        @JsonAlias("key")
        private String objectId;

        @JsonAlias("max#usage")
        private UsageApiResponse.Value usage;
    }

    @Getter
    @ToString
    public static class TopNResponse {

        private final String objectId;

        private final Double usage;

        public TopNResponse(TopNApiResponse.Bucket bucket) {
            this.objectId = bucket.getObjectId();
            this.usage = bucket.getUsage().getValue();
        }
    }

    @Getter
    static class Value {
        private Double value;
    }
}
