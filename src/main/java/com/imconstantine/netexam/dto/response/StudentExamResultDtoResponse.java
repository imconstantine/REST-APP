package com.imconstantine.netexam.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imconstantine.netexam.model.AnswerType;

import java.util.List;

public class StudentExamResultDtoResponse {

    private int questionsCount;
    private int correct;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AnswerType> details;

    public StudentExamResultDtoResponse() {
        super();
    }

    public StudentExamResultDtoResponse(int questionsCount, int correct, List<AnswerType> details) {
        this();
        setQuestionsCount(questionsCount);
        setCorrect(correct);
        setDetails(details);
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(int questionsCount) {
        this.questionsCount = questionsCount;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public List<AnswerType> getDetails() {
        return details;
    }

    public void setDetails(List<AnswerType> details) {
        this.details = details;
    }
}
