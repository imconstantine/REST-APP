package com.imconstantine.netexam.dto.response;

import java.util.List;

public class ExamsAndTeachersDtoResponse {

    private List<ExamAndTeacherDtoResponse> list;

    public ExamsAndTeachersDtoResponse(List<ExamAndTeacherDtoResponse> list) {
        this.list = list;
    }

    public ExamsAndTeachersDtoResponse() {
        super();
    }

    //todo remove comment for prod @JsonValue
    public List<ExamAndTeacherDtoResponse> getList() {
        return list;
    }

    public void setList(List<ExamAndTeacherDtoResponse> list) {
        this.list = list;
    }
}
