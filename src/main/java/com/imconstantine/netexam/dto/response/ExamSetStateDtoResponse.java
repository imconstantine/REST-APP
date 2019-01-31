package com.imconstantine.netexam.dto.response;

public class ExamSetStateDtoResponse {

    private Integer questionsCountPerExam;
    private Integer timeInMinutes;

    public ExamSetStateDtoResponse() {
        super();
    }

    public ExamSetStateDtoResponse(Integer questionsCountPerExam, Integer timeInMinutes) {
        this.questionsCountPerExam = questionsCountPerExam;
        this.timeInMinutes = timeInMinutes;
    }

    public Integer getQuestionsCountPerExam() {
        return questionsCountPerExam;
    }

    public void setQuestionsCountPerExam(Integer questionsCountPerExam) {
        this.questionsCountPerExam = questionsCountPerExam;
    }

    public Integer getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(Integer timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }
}
