package com.imconstantine.netexam.dto.request;

import com.imconstantine.netexam.utils.validator.*;

public class TeacherSignUpDtoRequest implements UserDtoRequest {

    @FirstNameConstraint
    private String firstName;

    @LastNameConstraint
    private String lastName;

    @PatronymicConstraint
    private String patronymic;

    @LoginConstraint
    private String login;

    @PasswordConstraint
    private String password;

    @DepartmentConstraint
    private String department;

    @PositionConstraint
    private String position;

    private String passwordHashKey;

    public TeacherSignUpDtoRequest() {
        super();
    }

    public TeacherSignUpDtoRequest(String firstName, String lastName, String patronymic, String login, String password,
                                   String department, String position) {
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setLogin(login);
        setPassword(password);
        setDepartment(department);
        setPosition(position);
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passsword) {
        this.password = passsword;
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
