package com.imconstantine.netexam.model;

import com.imconstantine.netexam.utils.ConverterUtils;

import java.util.List;
import java.util.Objects;

public class Student extends User {

    private Integer semester;
    private String group;
    private List<Solution> solutions;
    private List<AnswerType> result;

    public Student() {
        super();
    }

    public Student(String firstName, String lastName, String patronymic, String login, String hashPassword, String group, Integer semester, UserType type) {
        this(0, firstName, lastName, patronymic, login, hashPassword, group, semester, type);
    }

    public Student(int id, String firstName, String lastName, String patronymic, String login, String hashPassword, String group, Integer semester, UserType type) {
        super(id, firstName, lastName, patronymic, login, hashPassword, type);
        setGroup(group);
        setSemester(semester);
    }

    public List<AnswerType> getResult() {
        return result;
    }

    public void setResult(List<AnswerType> result) {
        this.result = result;
    }

    public void setTmpResult(byte[] tmpResult) {
        this.result = ConverterUtils.convertByteArrayToModel(tmpResult);
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return getSemester() == student.getSemester() &&
                getGroup() == student.getGroup();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSemester(), getGroup());
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", patronymic='" + super.getPatronymic() + '\'' +
                ", login='" + super.getLogin() + '\'' +
                ", semester='" + semester + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
