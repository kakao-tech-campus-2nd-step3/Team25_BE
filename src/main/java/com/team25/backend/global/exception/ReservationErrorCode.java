package com.team25.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReservationErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 예약 번호입니다."),
    USER_HAS_NO_RESERVATIONS(HttpStatus.NOT_FOUND, "해당 회원은 예약 사항이 없습니다."),
    RESERVATION_NOT_BELONG_TO_USER(HttpStatus.BAD_REQUEST, "해당 회원의 예약 번호가 아닙니다."),
    MANAGER_REQUIRED(HttpStatus.BAD_REQUEST, "매니저 선택은 필수 값입니다."),
    MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 매니저를 찾을 수 없습니다."),
    RESERVATION_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "이미 취소된 예약입니다"),
    CANCEL_REASON_REQUIRED(HttpStatus.BAD_REQUEST, "변심 이유를 반드시 선택해야 합니다."),
    INVALID_DATETIME_FORMAT(HttpStatus.BAD_REQUEST, "날짜 시간 형식이 올바르지 않습니다."),
    RESERVATION_WITHOUT_REPORT(HttpStatus.NOT_FOUND, "해당 예약에 대한 리포트가 없습니다."),
    RESERVATION_WITHOUT_ACCOMPANY(HttpStatus.NOT_FOUND, "해당 예약에 대한 실시간 동행 현황 정보가 없습니다."),
    INVALID_RESERVATION_STATUS(HttpStatus.BAD_REQUEST,"유효하지 않은 예약 상태입니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
