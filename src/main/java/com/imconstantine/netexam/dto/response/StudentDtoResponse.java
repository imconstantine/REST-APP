package com.imconstantine.netexam.dto.response;

public class StudentDtoResponse implements UserDto {

    private String firstName;
    private String lastName;
    private String patronymic;
    private String group;
    private int semester;
    private String type;

    public StudentDtoResponse() {
        super();
    }

    public StudentDtoResponse(String firstName, String lastName, String patronymic, String group, int semester, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.group = group;
        this.semester = semester;
        this.type = type;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int  getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getType() {
        return type;
    }

    public void setType(String userType) {
        this.type = userType;
    }

}
