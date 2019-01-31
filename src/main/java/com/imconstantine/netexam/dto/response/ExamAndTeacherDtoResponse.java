package com.imconstantine.netexam.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ExamAndTeacherDtoResponse {

    private int id;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TeacherDtoResponse teacher;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String patronymic;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String department;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String position;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer questionsCountPerExam;

    private int timeInMinutes;

    public ExamAndTeacherDtoResponse(int id, String name, String firstName, String lastName, String patronymic,
                                     String department, String position, Integer questionsCountPerExam, int timeInMinutes) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.department = department;
        this.position = position;
        this.questionsCountPerExam = questionsCountPerExam;
        this.timeInMinutes = timeInMinutes;
    }

    public ExamAndTeacherDtoResponse(int id, String name, TeacherDtoResponse teacher, int timeInMinutes) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.timeInMinutes = timeInMinutes;
    }

    public ExamAndTeacherDtoResponse() {
        super();
    }

    public TeacherDtoResponse getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDtoResponse teacher) {
        this.teacher = teacher;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getQuestionsCountPerExam() {
        return questionsCountPerExam;
    }

    public void setQuestionsCountPerExam(Integer questionsCountPerExam) {
        this.questionsCountPerExam = questionsCountPerExam;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }
}
