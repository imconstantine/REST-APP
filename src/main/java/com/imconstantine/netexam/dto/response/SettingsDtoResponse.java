package com.imconstantine.netexam.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class SettingsDtoResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer maxNameLength;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer minPasswordLength;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer minAnswers;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer minQuestionsCountPerExam;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer minTime;

    public SettingsDtoResponse() {
        super();
    }

    public SettingsDtoResponse(Integer maxNameLength, Integer minPasswordLength, Integer minAnswers,
                               Integer minQuestionsCountPerExam, Integer minTime) {
        this.maxNameLength = maxNameLength;
        this.minPasswordLength = minPasswordLength;
        this.minAnswers = minAnswers;
        this.minQuestionsCountPerExam = minQuestionsCountPerExam;
        this.minTime = minTime;
    }

    public Integer getMaxNameLength() {
        return maxNameLength;
    }

    public void setMaxNameLength(Integer maxNameLength) {
        this.maxNameLength = maxNameLength;
    }

    public Integer getMinPasswordLength() {
        return minPasswordLength;
    }

    public void setMinPasswordLength(Integer minPasswordLength) {
        this.minPasswordLength = minPasswordLength;
    }

    public Integer getMinAnswers() {
        return minAnswers;
    }

    public void setMinAnswers(Integer minAnswers) {
        this.minAnswers = minAnswers;
    }

    public Integer getMinQuestionsCountPerExam() {
        return minQuestionsCountPerExam;
    }

    public void setMinQuestionsCountPerExam(Integer minQuestionsCountPerExam) {
        this.minQuestionsCountPerExam = minQuestionsCountPerExam;
    }

    public Integer getMinTime() {
        return minTime;
    }

    public void setMinTime(Integer minTime) {
        this.minTime = minTime;
    }
}
