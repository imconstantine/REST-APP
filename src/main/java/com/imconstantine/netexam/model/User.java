package com.imconstantine.netexam.model;

import java.util.Objects;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String login;
    private String passwordHashKey;
    private UserType type;

    public User() {
        super();
    }

    public User(String firstName, String lastName, String patronymic, String login, String password, UserType type) {
        this(0, firstName, lastName, patronymic, login, password, type);
    }

    public User(int id, String firstName, String lastName, String patronymic, String login, String password, UserType type) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setLogin(login);
        setPasswordHashKey(password);
        setType(type);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.login = login.toLowerCase();
    }

    public String getPasswordHashKey() {
        return passwordHashKey;
    }

    public void setPasswordHashKey(String passwordHashKey) {
        this.passwordHashKey = passwordHashKey;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getPatronymic(), user.getPatronymic()) &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPasswordHashKey(), user.getPasswordHashKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getPatronymic(), getLogin(), getPasswordHashKey());
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", login='" + login + '\'' +
                ", passwordHashKey='" + passwordHashKey + '\'' +
                '}';
    }
}
