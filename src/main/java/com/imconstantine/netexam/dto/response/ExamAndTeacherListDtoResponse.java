package com.imconstantine.netexam.dto.response;

import java.util.List;

public class ExamAndTeacherListDtoResponse {

    private List<ExamAndTeacherDtoResponse> examAndTeacherDtoResponses;

    public ExamAndTeacherListDtoResponse(List<ExamAndTeacherDtoResponse> examAndTeacherDtoResponses) {
        this.examAndTeacherDtoResponses = examAndTeacherDtoResponses;
    }

    public List<ExamAndTeacherDtoResponse> getExamAndTeacherDtoResponses() {
        return examAndTeacherDtoResponses;
    }

    public void setExamAndTeacherDtoResponses(List<ExamAndTeacherDtoResponse> examAndTeacherDtoResponses) {
        this.examAndTeacherDtoResponses = examAndTeacherDtoResponses;
    }
}
