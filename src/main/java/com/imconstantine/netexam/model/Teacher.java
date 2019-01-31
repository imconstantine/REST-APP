package com.imconstantine.netexam.model;

import java.util.List;
import java.util.Objects;

public class Teacher extends User {

    private String department;
    private String position;
    private List<Exam> examList;

    public Teacher() {
        super();
    }

    public Teacher(String firstName, String lastName, String patronymic, String login, String hashPassword, String department, String position, UserType type) {
        this(0, firstName, lastName, patronymic, login, hashPassword, department, position, type);
    }

    public Teacher(int id, String firstName, String lastName, String patronymic, String login, String hashPassword, String department, String position, UserType type) {
        super(id, firstName, lastName, patronymic, login, hashPassword, type);
        setDepartment(department);
        setPosition(position);
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getPosition(), teacher.getPosition()) &&
                Objects.equals(getDepartment(), teacher.getDepartment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPosition(), getDepartment());
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", patronymic='" + super.getPatronymic() + '\'' +
                ", login='" + super.getLogin() + '\'' +
                ", department='" + department + '\'' +
                ", employment='" + position + '\'' +
                '}';
    }
}
