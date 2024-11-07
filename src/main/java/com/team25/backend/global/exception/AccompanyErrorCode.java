package com.team25.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccompanyErrorCode {
    INVALID_ACCOMPANY_STATUS(HttpStatus.BAD_REQUEST, "올바르지 않은 실시간 동행 진행 상태입니다."),
    INVALID_LATITUDE(HttpStatus.BAD_REQUEST, "위도는 0이상 90 이하의 값입니다."),
    INVALID_LONGITUDE(HttpStatus.BAD_REQUEST, "경도는 0이상 360 이하의 값입니다."),
    REQUIRED_DATE_MISSING(HttpStatus.BAD_REQUEST, "시간은 필수 입력 값입니다."),
    REQUIRED_DESCRIPTION_MISSING(HttpStatus.BAD_REQUEST, "리포트 상세 사항은 필수 사항입니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
