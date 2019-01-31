package com.imconstantine.netexam.dto.response;

import java.util.List;

public class StudentExamResultListDtoResponse {

    private List<StudentResultMainDtoResponse> studentResultMainDtoResponses;

    public StudentExamResultListDtoResponse() {
        super();
    }

    public StudentExamResultListDtoResponse(List<StudentResultMainDtoResponse> studentResultMainDtoResponses) {
        this.studentResultMainDtoResponses = studentResultMainDtoResponses;
    }

    //todo remove for prod @JsonValue
    public List<StudentResultMainDtoResponse> getStudentResultMainDtoResponses() {
        return studentResultMainDtoResponses;
    }

    public void setStudentResultMainDtoResponses(List<StudentResultMainDtoResponse> studentResultMainDtoResponses) {
        this.studentResultMainDtoResponses = studentResultMainDtoResponses;
    }
}
