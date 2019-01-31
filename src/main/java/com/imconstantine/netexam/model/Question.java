package com.imconstantine.netexam.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {

    private int id;

    private Integer number;
    private String content;
    private List<Answer> answerList;
    private Integer correct;

    public Question() {
        super();
        answerList = new ArrayList<>();
    }

    public Question(Integer number, String content, List<Answer> answerList, Integer correct) {
        this.number = number;
        this.content = content;
        this.answerList = answerList;
        this.correct = correct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return getId() == question.getId() &&
                Objects.equals(getNumber(), question.getNumber()) &&
                Objects.equals(getContent(), question.getContent()) &&
                Objects.equals(getAnswerList(), question.getAnswerList()) &&
                Objects.equals(getCorrect(), question.getCorrect());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getContent(), getAnswerList(), getCorrect());
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", number=" + number +
                ", content='" + content + '\'' +
                ", answerList=" + answerList +
                ", correct=" + correct +
                '}';
    }
}
