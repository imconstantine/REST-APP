package com.imconstantine.netexam.dto.response;

import java.util.List;

public class ExamsParamDtoResponse {

    private List<ExamParamDtoResponse> list;

    public ExamsParamDtoResponse(List<ExamParamDtoResponse> list) {
        this.list = list;
    }

    public ExamsParamDtoResponse() {
        super();
    }


    //todo remove comment for prod @JsonValue
    public List<ExamParamDtoResponse> getList() {
        return list;
    }

    public void setList(List<ExamParamDtoResponse> list) {
        this.list = list;
    }
}
