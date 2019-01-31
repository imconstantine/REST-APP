package com.imconstantine.netexam.dto.request;

import com.imconstantine.netexam.utils.validator.*;

public class StudentUpdateDtoRequest {

    @PasswordConstraint
    private String oldPassword;

    @PasswordConstraint
    private String newPassword;

    @FirstNameConstraint
    private String firstName;

    @LastNameConstraint
    private String lastName;

    @PatronymicConstraint
    private String patronymic;

    @SemesterConstraint
    private Integer semester;

    private String group;
    private String passwordHashKey;

    public StudentUpdateDtoRequest() {
        super();
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPasswordHashKey() {
        return passwordHashKey;
    }

    public void setPasswordHashKey(String passwordHashKey) {
        this.passwordHashKey = passwordHashKey;
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

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }
}
