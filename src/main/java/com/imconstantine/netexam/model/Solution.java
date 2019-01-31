package com.imconstantine.netexam.model;

import java.util.List;

public class Solution {

    private int examId;
    private List<AnswerType> results;

    public Solution() {
        super();
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public List<AnswerType> getResults() {
        return results;
    }

    public void setResults(List<AnswerType> results) {
        this.results = results;
    }

    public Solution(int examId, List<AnswerType> results) {
        this.examId = examId;
        this.results = results;
    }
}
