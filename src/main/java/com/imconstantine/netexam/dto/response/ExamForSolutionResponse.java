package com.imconstantine.netexam.dto.response;

import java.util.List;

public class ExamForSolutionResponse {

    private ExamAndTeacherDtoResponse exam;
    private List<QuestionDtoResponse> questions;

    public ExamForSolutionResponse(ExamAndTeacherDtoResponse exam, List<QuestionDtoResponse> questions) {
        this.exam = exam;
        this.questions = questions;
    }

    public ExamAndTeacherDtoResponse getExam() {
        return exam;
    }

    public void setExam(ExamAndTeacherDtoResponse exam) {
        this.exam = exam;
    }

    public List<QuestionDtoResponse> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDtoResponse> questions) {
        this.questions = questions;
    }
}
