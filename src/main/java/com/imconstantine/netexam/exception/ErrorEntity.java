package com.imconstantine.netexam.exception;

import com.imconstantine.netexam.utils.ErrorCode;

public class ErrorEntity {

    private ErrorCode errorCode;
    private String field;
    private String message;

    public ErrorEntity(ErrorCode errorCode) {
        setErrorCode(errorCode);
        setField(errorCode.getField());
        setMessage(errorCode.getMessage());
    }

    public ErrorEntity(ErrorCode errorCode, String field) {
        setErrorCode(errorCode);
        setField(errorCode.getField());
        setMessage(String.format(errorCode.getMessage(), field));
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
