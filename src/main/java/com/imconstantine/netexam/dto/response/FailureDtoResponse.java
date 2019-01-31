package com.imconstantine.netexam.dto.response;

import java.util.List;

public class FailureDtoResponse {

    private List<ErrorEntityDtoResponse> errorList;

    public FailureDtoResponse() {
        super();
    }

    public List<ErrorEntityDtoResponse> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<ErrorEntityDtoResponse> errorList) {
        this.errorList = errorList;
    }
}
