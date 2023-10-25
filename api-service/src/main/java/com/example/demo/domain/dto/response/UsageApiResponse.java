package com.example.demo.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class UsageApiResponse {

   private final String hostId;
   private List<UsageResponse> result;



   @Getter
   @ToString
   public static class Bucket {
      @JsonAlias("key_as_string")
      private String timestamp;

      @JsonAlias("min#vm_hypervisor_status_memory_utillization")
      private Value minMemoryUsage;

      @JsonAlias("min#vm_hypervisor_status_cpu_utillization")
      private Value minCpuUsage;

      @JsonAlias("max#vm_hypervisor_status_memory_utillization")
      private Value maxMemoryUsage;

      @JsonAlias("max#vm_hypervisor_status_cpu_utillization")
      private Value maxCpuUsage;
   }

   @Getter
   @ToString
   public static class UsageResponse {

      private final String timestamp;

      @JsonInclude(JsonInclude.Include.NON_NULL)
      private final Double minMemoryUsage;
      @JsonInclude(JsonInclude.Include.NON_NULL)
      private final Double minCpuUsage;
      @JsonInclude(JsonInclude.Include.NON_NULL)
      private final Double maxMemoryUsage;
      @JsonInclude(JsonInclude.Include.NON_NULL)
      private final Double maxCpuUsage;

      public UsageResponse(Bucket bucket) {
         this.timestamp = bucket.getTimestamp();
         this.minMemoryUsage = Objects.nonNull(bucket.getMinMemoryUsage()) ? bucket.getMinMemoryUsage().getValue() : null;
         this.minCpuUsage = Objects.nonNull(bucket.getMinCpuUsage()) ? bucket.getMinCpuUsage().getValue() : null;
         this.maxMemoryUsage = Objects.nonNull(bucket.getMaxMemoryUsage()) ? bucket.getMaxMemoryUsage().getValue() : null;
         this.maxCpuUsage = Objects.nonNull(bucket.getMaxCpuUsage()) ? bucket.getMaxCpuUsage().getValue():  null;
      }
   }

   @Getter
   static class Value {
      private Double value;
   }
}
