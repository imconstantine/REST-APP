package com.imconstantine.netexam.dto.request;

import javax.validation.constraints.DecimalMin;
import java.util.List;

public class QuestionDtoRequest {

    private String question;

    @DecimalMin(value = "0", message = "NOT_VALID_QUESTION_NUMBER")
    private Integer number;

    private List<String> answers;

    private Integer correct;

    public QuestionDtoRequest() {
        super();
    }

    public QuestionDtoRequest(String question, Integer number, List<String> answers, Integer correct) {
        this.question = question;
        this.number = number;
        this.answers = answers;
        this.correct = correct;
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
        if (number == null) this.number = 0;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }
}
