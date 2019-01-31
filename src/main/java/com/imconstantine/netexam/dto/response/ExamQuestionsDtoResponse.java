package com.imconstantine.netexam.dto.response;

import java.util.List;

public class ExamQuestionsDtoResponse {

    private List<QuestionDtoResponse> questionDtoResponses;

    public ExamQuestionsDtoResponse() {
        super();
    }

    public ExamQuestionsDtoResponse(List<QuestionDtoResponse> dtoResponseList) {
        questionDtoResponses = dtoResponseList;
    }

    public List<QuestionDtoResponse> getQuestionDtoResponses() {
        return questionDtoResponses;
    }

    public void setQuestionDtoResponses(List<QuestionDtoResponse> questionDtoResponses) {
        this.questionDtoResponses = questionDtoResponses;
    }
}
