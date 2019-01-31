package com.imconstantine.netexam.dto.request;

import com.imconstantine.netexam.utils.validator.SemesterConstraint;

public class ExamUpdateDtoRequest {

    private Integer id;

    private String name;

    @SemesterConstraint
    private Integer semester;

    public ExamUpdateDtoRequest() {
        super();
    }

    public ExamUpdateDtoRequest(String name, Integer semester) {
        this.name = name;
        this.semester = semester;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
