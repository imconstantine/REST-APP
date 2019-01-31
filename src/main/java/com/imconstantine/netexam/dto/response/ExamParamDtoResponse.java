package com.imconstantine.netexam.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ExamParamDtoResponse {

    private String name;
    private int id;
    private int semester;
    private boolean ready;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean showDetails;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer questionsCountPerExam;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer timeInMinutes;

    public ExamParamDtoResponse(String name, int id, int semester, boolean ready, Integer questionsCountPerExam, Integer timeInMinutes, boolean showDetails) {
        this.name = name;
        this.id = id;
        this.semester = semester;
        this.ready = ready;
        this.questionsCountPerExam = questionsCountPerExam;
        this.timeInMinutes = timeInMinutes;
        if (showDetails) {
            this.showDetails = null;
        } else {
            this.showDetails = false;
        }
    }

    public ExamParamDtoResponse() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Boolean isShowDetails() {
        return showDetails;
    }

    public void setShowDetails(Boolean showDetails) {
        this.showDetails = showDetails;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
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
