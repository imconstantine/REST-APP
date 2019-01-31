package com.imconstantine.netexam.dto.request;

import com.imconstantine.netexam.utils.validator.LoginConstraint;
import com.imconstantine.netexam.utils.validator.PasswordConstraint;

import javax.validation.constraints.NotNull;

public class LoginDtoRequest {

    @NotNull(message = "LOGIN_IS_NULL")
    @LoginConstraint
    private String login;

    @NotNull(message = "PASSWORD_IS_NULL")
    @PasswordConstraint
    private String password;

    private String passwordHashKey;

    public LoginDtoRequest() {
        super();
    }

    public LoginDtoRequest(String login, String password) {
        this.login = login;
        this.password = password;
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

    public String getPasswordHashKey() {
        return passwordHashKey;
    }

    public void setPasswordHashKey(String passwordHashKey) {
        this.passwordHashKey = passwordHashKey;
    }
}
