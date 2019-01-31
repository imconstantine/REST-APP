package com.imconstantine.netexam.model;

import com.imconstantine.netexam.utils.ConverterUtils;

import java.util.List;

public class ExamInfo {

    private int id;
    private String name;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String department;
    private String position;
    private boolean showDetails;
    private int questionsCountPerExam;
    private int timeInMinutes;
    private List<AnswerType> result;

    public ExamInfo() {
        super();
    }

    public void setTmpResult(byte[] tmpResult) {
        this.result = ConverterUtils.convertByteArrayToModel(tmpResult);
    }

    public List<AnswerType> getResult() {
        return result;
    }

    public void setResult(List<AnswerType> result) {
        this.result = result;
    }

    public boolean isShowDetails() {
        return showDetails;
    }

    public void setShowDetails(boolean showDetails) {
        this.showDetails = showDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getQuestionsCountPerExam() {
        return questionsCountPerExam;
    }

    public void setQuestionsCountPerExam(int questionsCountPerExam) {
        this.questionsCountPerExam = questionsCountPerExam;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }
}
