package com.imconstantine.netexam.exception;

import com.imconstantine.netexam.utils.ErrorCode;

import java.util.ArrayList;
import java.util.List;

public class NetExamException extends Exception {

    private final List<ErrorEntity> exceptionList = new ArrayList<>();

    public NetExamException() {
        super();
    }

    public NetExamException(ErrorCode errorCode) {
        this();
        exceptionList.add(new ErrorEntity(errorCode));
    }

    public NetExamException(ErrorCode errorCode, String field) {
        this();
        exceptionList.add(new ErrorEntity(errorCode, field));
    }

    public NetExamException(ErrorCode errorCode, Integer field) {
        this();
        exceptionList.add(new ErrorEntity(errorCode, "" + field));
    }

    public void add(InvalidationException exception) {
        if (exception.getField() == null) {
            add(exception.getErrorCode());
        } else {
            add(exception.getErrorCode());
        }
    }

    public void add(ErrorCode errorCode, String field) {
        exceptionList.add(new ErrorEntity(errorCode, field));
    }

    public void add(ErrorCode errorCode) {
        exceptionList.add(new ErrorEntity(errorCode));
    }

    public void add(ErrorCode errorCode, Integer field) {
        exceptionList.add(new ErrorEntity(errorCode, "" + field));
    }

    public List<ErrorEntity> getExceptionList() {
        return exceptionList;
    }

}
