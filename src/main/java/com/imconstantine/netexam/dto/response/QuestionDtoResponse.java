package com.imconstantine.netexam.dto.response;

import java.util.ArrayList;
import java.util.List;

public class QuestionDtoResponse {

    private String question;
    private Integer number;
    private int id;
    private List<String> answerList;

    //todo remove for prod
    private Integer correct;

    public QuestionDtoResponse() {
        super();
        answerList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }
}
