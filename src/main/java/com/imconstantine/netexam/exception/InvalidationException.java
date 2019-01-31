package com.imconstantine.netexam.exception;

import com.imconstantine.netexam.utils.ErrorCode;

public class InvalidationException extends Exception {

    private ErrorCode errorCode;
    private String field;

    public InvalidationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public InvalidationException(ErrorCode errorCode, String field) {
        this.errorCode = errorCode;
        this.field = field;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getField() {
        return field;
    }
}
