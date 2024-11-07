package com.team25.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReportErrorCode {
    REQUIRED_DOCTOR_SUMMARY_MISSING(HttpStatus.BAD_REQUEST, "의사 소견은 1글자 이상이어야 합니다."),
    INVALID_FREQUENCY(HttpStatus.BAD_REQUEST, "복약 횟수는 숫자여야 합니다."),
    INVALID_MEDICINE_TIME(HttpStatus.BAD_REQUEST, "복약 시간은 식전 30분, 식후 30분, 식간이어야합니다."),
    REQUIRED_TIME_OF_DAYS_MISSING(HttpStatus.BAD_REQUEST, "복약은 아침, 점심, 저녁 등으로 설정하여야 하고 1글자 이상이어야합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
