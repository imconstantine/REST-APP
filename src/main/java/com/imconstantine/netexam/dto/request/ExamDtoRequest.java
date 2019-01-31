package com.imconstantine.netexam.dto.request;

import com.imconstantine.netexam.utils.validator.SemesterConstraint;

import javax.validation.constraints.*;

public class ExamDtoRequest {

    @NotNull(message = "EXAM_NAME_IS_NULL")
    private String name;

    @NotNull(message = "SEMESTER_IS_NULL")
    @SemesterConstraint
    private Integer semester;

    private Integer sourceId;

    public ExamDtoRequest() {
        super();
    }

    public ExamDtoRequest(String name, Integer semester, Integer sourceId) {
        this.name = name;
        this.semester = semester;
        this.sourceId = sourceId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

}
