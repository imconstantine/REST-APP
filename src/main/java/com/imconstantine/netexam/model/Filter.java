package com.imconstantine.netexam.model;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.utils.ErrorCode;

public enum Filter {
    ALL,
    PASSED,
    REMAINING,
    CURRENT;

    public static Filter getFilter(String filterValue) throws NetExamException {
        try {
            if (filterValue == null) {
                filterValue = "ALL";
            }
            return Filter.valueOf(filterValue);
        } catch (IllegalArgumentException exception) {
            throw new NetExamException(ErrorCode.INVALID_FILTER);
        }
    }
}
