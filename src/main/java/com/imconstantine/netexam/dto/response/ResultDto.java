package com.imconstantine.netexam.dto.response;

public class ResultDto {

    private int correct;
    private int wrong;
    private int noAnswer;

    public ResultDto(int correct, int wrong, int noAnswer) {
        this.correct = correct;
        this.wrong = wrong;
        this.noAnswer = noAnswer;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public int getNoAnswer() {
        return noAnswer;
    }

    public void setNoAnswer(int noAnswer) {
        this.noAnswer = noAnswer;
    }
}
