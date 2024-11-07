package com.team25.backend.global.exception;

import lombok.Getter;

@Getter
public class AccompanyException extends RuntimeException {

    private final AccompanyErrorCode errorCode;

    public AccompanyException(AccompanyErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
