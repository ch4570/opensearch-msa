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
public class TopNSearchCondition {

    private String option;
    private boolean sort;
    private int size;


    public static class TopNSearchConditionBuilder{
        private String option;
        private boolean sort;
        private int size;

        public TopNSearchConditionBuilder option(String option) {
            if (!StringUtils.hasText(option) || !(option.equals("disk") || option.equals("memory") || option.equals("cpu"))) {
                throw new CustomException(ILLEGAL_OPTION_TOPN_BAD_REQUEST);
            }
            this.option = option;
            return this;
        }

        public TopNSearchConditionBuilder sort(String sort) {
            if (!StringUtils.hasText(sort) || !(sort.equals("asc") || sort.equals("desc"))) {
                throw new CustomException(ILLEGAL_SORT_TOPN_BAD_REQUEST);
            }

            this.sort = !sort.equals("desc");
            return this;
        }

        public TopNSearchConditionBuilder size(String size) {
            int requestSize = Integer.parseInt(size);
            if (requestSize < 1 || requestSize > 50) throw new CustomException(ILLEGAL_RANGE_TOPN_BAD_REQUEST);
            this.size = requestSize;
            return this;
        }

        public TopNSearchCondition build() {
            return new TopNSearchCondition(option, sort, size);
        }

    }
}
