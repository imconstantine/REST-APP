package com.imconstantine.netexam.dto.request;

import com.imconstantine.netexam.utils.validator.QuestionsCountPerExamConstraint;
import com.imconstantine.netexam.utils.validator.TimeInMinutesConstraint;

import javax.validation.constraints.NotNull;

public class ExamSetStateDtoRequest {

    @QuestionsCountPerExamConstraint
    private Integer questionsCountPerExam;

    @TimeInMinutesConstraint
    private Integer timeInMinutes;

    @NotNull(message = "EXAM_DETAILS_IS_NULL")
    private Boolean showDetails;

    public ExamSetStateDtoRequest() {
         super();
    }

    public ExamSetStateDtoRequest(Integer questionsCountPerExam, Integer timeInMinutes, Boolean showDetails) {
        this.questionsCountPerExam = questionsCountPerExam;
        this.timeInMinutes = timeInMinutes;
        this.showDetails = showDetails;
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

    public Boolean getShowDetails() {
        return showDetails;
    }

    public void setShowDetails(Boolean showDetails) {
        this.showDetails = showDetails;
    }
}
