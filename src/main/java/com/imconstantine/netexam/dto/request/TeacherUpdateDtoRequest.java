package com.imconstantine.netexam.dto.request;

import com.imconstantine.netexam.utils.validator.FirstNameConstraint;
import com.imconstantine.netexam.utils.validator.LastNameConstraint;
import com.imconstantine.netexam.utils.validator.PasswordConstraint;
import com.imconstantine.netexam.utils.validator.PatronymicConstraint;

public class TeacherUpdateDtoRequest {

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

    private String department;
    private String position;
    private String passwordHashKey;

    public TeacherUpdateDtoRequest() {
        super();
    }

    public String getPasswordHashKey() {
        return passwordHashKey;
    }

    public void setPasswordHashKey(String passwordHashKey) {
        this.passwordHashKey = passwordHashKey;
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
}
