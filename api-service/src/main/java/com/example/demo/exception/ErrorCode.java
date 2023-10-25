package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    /* 400 BAD_REQUEST */
    ILLEGAL_RANGE_USAGE_BAD_REQUEST(BAD_REQUEST, "조회 범위가 잘못 되었습니다. 시간은 [1-24], 분은 [1-60] 범위 값을 입력해주세요"),
    ILLEGAL_HOSTNAME_BAD_REQUEST(BAD_REQUEST, "Host ID는 필수 입력 값 입니다."),
    ILLEGAL_INPUT_TYPE_USAGE_BAD_REQUEST(BAD_REQUEST, "조회 타입은 비어있거나, [min, max] 가 아닌 값이 들어오지 않아야 합니다."),
    ILLEGAL_OPTION_USAGE_BAD_REQUEST(BAD_REQUEST, "조회 옵션은 비어있거나, [all, ram, cpu] 가 아닌 값이 들어오지 않아야 합니다."),
    ILLEGAL_OPTION_TOPN_BAD_REQUEST(BAD_REQUEST, "조회 옵션은 비어있거나, [disk, memory, cpu] 이 아닌 값이 들어오지 않아야 합니다."),
    ILLEGAL_SORT_TOPN_BAD_REQUEST(BAD_REQUEST, "정렬 기준은 비어있거나, [asc, desc] 가 아닌 값이 들어오지 않아야 합니다."),
    ILLEGAL_RANGE_TOPN_BAD_REQUEST(BAD_REQUEST, "데이터 리스트의 크기는 [1-50] 범위 값을 입력해주세요"),
    ILLEGAL_OBJECTID_AGGREGATE_BAD_REQUEST(BAD_REQUEST, "ObjectID는 필수 입력 값입니다."),

    /* 500 SERVER ERROR */
    SEARCH_SERVER_ERROR(INTERNAL_SERVER_ERROR, "조회 중 문제가 발생했습니다. 다시 시도해주시기 바랍니다.");

    private final HttpStatus httpStatus;
    private final String message;


}
