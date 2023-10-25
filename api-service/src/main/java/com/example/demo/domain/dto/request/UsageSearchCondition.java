package com.example.demo.domain.dto.request;

import com.example.demo.exception.CustomException;
import static com.example.demo.exception.ErrorCode.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.Optional;


@Getter
@ToString
@AllArgsConstructor
public class UsageSearchCondition {

    private String hostId;
    private String type;
    private int interval;
    private int from;
    private String option;


    public static class UsageConditionBuilder {
        private String hostId;
        private String type;
        private int interval;
        private int from;
        private String option;

        public UsageConditionBuilder hostId(String hostId) {
            if (!StringUtils.hasText(hostId)) throw new CustomException(ILLEGAL_HOSTNAME_BAD_REQUEST);
            this.hostId = hostId;
            return this;
        }

        public UsageConditionBuilder type(String type) {
            if (!StringUtils.hasText(type) || !(type.equals("max") || type.equals("min"))) {
                throw new CustomException(ILLEGAL_INPUT_TYPE_USAGE_BAD_REQUEST);
            }
            this.type = type;
            return this;
        }

        public UsageConditionBuilder interval(String interval) {
            int requestInterval = Integer.parseInt(interval);
            if (requestInterval > 60 || requestInterval < 1) throw new CustomException(ILLEGAL_RANGE_USAGE_BAD_REQUEST);
            this.interval = requestInterval;
            return this;
        }

        public UsageConditionBuilder from(String from) {
            int requestFromTime = Integer.parseInt(from);
            if (requestFromTime > 24 || requestFromTime < 1) throw new CustomException(ILLEGAL_RANGE_USAGE_BAD_REQUEST);
            this.from = requestFromTime;
            return this;
        }

        public UsageConditionBuilder option(String option) {
            if (!StringUtils.hasText(option) || !(option.equals("ram") || option.equals("cpu") || option.equals("all"))) {
                throw new CustomException(ILLEGAL_OPTION_USAGE_BAD_REQUEST);
            }
            this.option = option;
            return this;
        }

        public UsageSearchCondition build() {
            return new UsageSearchCondition(hostId, type, interval, from, option);
        }
    }
}
