package com.imconstantine.netexam.dto.request;

import com.imconstantine.netexam.utils.validator.*;

import javax.validation.constraints.NotBlank;

public class StudentSignUpDtoRequest implements UserDtoRequest {

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

    @NotBlank(message = "GROUP_IS_NULL")
    private String group;

    @SemesterConstraint
    private Integer semester;

    private String hashKey;

    public StudentSignUpDtoRequest() {
        super();
    }

    public StudentSignUpDtoRequest(String firstName, String lastName, String patronymic, String login, String password, String group, Integer semester) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
        this.group = group;
        this.semester = semester;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
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

    public void setPassword(String password) {
        this.password = password;
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
