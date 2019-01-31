package com.imconstantine.netexam.dto.response;

import java.util.List;

public class GroupDto {

    private List<StudentAndResultDto> students;

    public GroupDto() {
        super();
    }

    public GroupDto(List<StudentAndResultDto> students) {
        this.students = students;
    }

    public List<StudentAndResultDto> getStudents() {
        return students;
    }

    public void setStudents(List<StudentAndResultDto> students) {
        this.students = students;
    }
}
