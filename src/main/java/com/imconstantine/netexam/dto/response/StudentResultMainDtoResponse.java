package com.imconstantine.netexam.dto.response;

public class StudentResultMainDtoResponse {

    private int id;
    private String name;
    private TeacherDtoResponse teacher;
    private StudentExamResultDtoResponse result;

    public StudentResultMainDtoResponse() {
        super();
    }

    public StudentResultMainDtoResponse(int id, String name, TeacherDtoResponse teacher, StudentExamResultDtoResponse result) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeacherDtoResponse getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDtoResponse teacher) {
        this.teacher = teacher;
    }

    public StudentExamResultDtoResponse getResult() {
        return result;
    }

    public void setResult(StudentExamResultDtoResponse result) {
        this.result = result;
    }
}
