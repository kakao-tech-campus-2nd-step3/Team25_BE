package com.team25.backend.exception;

import lombok.Getter;

@Getter
public class ManagerException extends RuntimeException {
    private final ManagerErrorCode errorCode;

    public ManagerException(ManagerErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
