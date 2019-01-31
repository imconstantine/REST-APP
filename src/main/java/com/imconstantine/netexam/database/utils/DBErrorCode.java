package com.imconstantine.netexam.database.utils;

public enum DBErrorCode {

    DUPLICATE_ENTRY(1062),
    VALUE_IS_NOT_EXISTS(0);

    private Integer code;

    DBErrorCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
