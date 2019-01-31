package com.imconstantine.netexam.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;


public class TeacherDtoResponse implements UserDto {

    private String firstName;
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String patronymic;
    private String department;
    private String position;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;

    public TeacherDtoResponse() {
        super();
    }

    public TeacherDtoResponse(String firstName, String lastName, String patronymic, String department, String position, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.department = department;
        this.position = position;
        this.type = type;
    }

    public TeacherDtoResponse(String firstName, String lastName, String patronymic, String department, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.department = department;
        this.position = position;
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

    public String getType() {
        return type;
    }

    public void setType(String userType) {
        this.type = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeacherDtoResponse)) return false;
        TeacherDtoResponse that = (TeacherDtoResponse) o;
        return Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getPatronymic(), that.getPatronymic()) &&
                Objects.equals(getDepartment(), that.getDepartment()) &&
                Objects.equals(getPosition(), that.getPosition()) &&
                Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getPatronymic(), getDepartment(), getPosition(), getType());
    }
}
