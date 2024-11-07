package com.team25.backend.global.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode {
    KAKAO_PLATFORM_ERROR(HttpStatus.BAD_REQUEST, "KAKAO PLATFORM INTERNAL ERROR"),
    KAKAO_TOKEN_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "BAD REQUEST FORMAT"),
    KAKAO_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN IS EXPIRED OR INVALID"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"회원이 존재하지 않습니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT,"회원이 이미 존재합니다."),
    RESPONSE_BODY_NULL(HttpStatus.INTERNAL_SERVER_ERROR,"API 요청 응답이 비어있습니다."),
    MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 매니저입니다.");


    private final HttpStatus status;
    private final String message;

    public HttpStatus getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
}
