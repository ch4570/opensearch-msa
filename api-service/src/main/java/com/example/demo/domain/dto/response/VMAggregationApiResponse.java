package com.example.demo.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VMAggregationApiResponse {

    private Cluster cluster;
    private VM vm;
    private Datastore datastore;
    private Host host;


    @Getter
    @ToString
    public static class Cluster {
        private int count;
    }

    @Getter
    @ToString
    public static class VM {
        @JsonAlias("running")
        private int runningCount;
        private int count;
    }

    @Getter
    @ToString
    public static class Datastore {
        private int count;
    }

    @Getter
    @ToString
    public static class Host {
        private int count;
    }
}
