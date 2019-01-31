package com.imconstantine.netexam.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imconstantine.netexam.utils.ErrorCode;

public class ErrorEntityDtoResponse {

    @JsonProperty("errorCode")
    private ErrorCode errorCode;

    @JsonProperty("field")
    private String field;

    @JsonProperty("message")
    private String message;

    public ErrorEntityDtoResponse() {
        super();
    }

    public ErrorEntityDtoResponse(ErrorCode errorCode) {
        setErrorCode(errorCode);
        setField(errorCode.getField());
        setMessage(errorCode.getMessage());
    }

    public ErrorEntityDtoResponse(ErrorCode errorCode, String field, String message) {
        this();
        setErrorCode(errorCode);
        setField(field);
        setMessage(message);
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
