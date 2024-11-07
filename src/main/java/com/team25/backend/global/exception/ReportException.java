package com.team25.backend.global.exception;

import lombok.Getter;

@Getter
public class ReportException extends RuntimeException {

    private final ReportErrorCode errorCode;

    public ReportException(ReportErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
