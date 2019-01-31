package com.imconstantine.netexam.dto.response;

public class StudentAndResultDto {
    private String firstName;
    private String lastName;
    private String patronymic;
    private int correct;
    private int wrong;
    private int noAnswer;

    public StudentAndResultDto() {
        super();
    }

    public StudentAndResultDto(String firstName, String lastName, String patronymic, int correct, int wrong, int noAnswer) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.correct = correct;
        this.wrong = wrong;
        this.noAnswer = noAnswer;
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

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public int getNoAnswer() {
        return noAnswer;
    }

    public void setNoAnswer(int noAnswer) {
        this.noAnswer = noAnswer;
    }
}
