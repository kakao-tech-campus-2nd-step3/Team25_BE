package com.team25.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ManagerErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력 값입니다."),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "날짜 형식이 옳지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "등록되지 않은 회원입니다."),
    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 지역입니다."),
    NAME_TOO_SHORT(HttpStatus.BAD_REQUEST, "이름은 1자 이상 입력해야 합니다."),
    MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 매니저입니다."),
    INVALID_WORKING_HOUR_FORMAT(HttpStatus.BAD_REQUEST, "근무 시작 시간은 숫자로 작성해야 합니다."),
    INVALID_PROFILE_IMAGE(HttpStatus.BAD_REQUEST, "프로필 사진 경로는 0자 이상이어야 합니다."),
    INVALID_COMMENT(HttpStatus.BAD_REQUEST, "코멘트는 0자 이상이어야 합니다."),
    INVALID_WORKING_REGION(HttpStatus.BAD_REQUEST, "근무 지역은 0자 이상이어야 합니다."),
    WORKING_HOUR_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 근무시간입니다."),
    INVALID_TIME_RANGE(HttpStatus.BAD_REQUEST, "근무 시작 시간은 종료 시간보다 앞서야 합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
