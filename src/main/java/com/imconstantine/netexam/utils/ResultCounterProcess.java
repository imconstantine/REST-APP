package com.imconstantine.netexam.utils;

import com.imconstantine.netexam.dto.response.ResultDto;
import com.imconstantine.netexam.model.AnswerType;

import java.util.List;

public class ResultCounterProcess {

    public static ResultDto count(List<AnswerType> result) {
        int correct = 0, wrong = 0, noAnswer = 0;
        for (AnswerType answerType : result) {
            if (answerType == AnswerType.YES) {
                correct++;
            } else if (answerType == AnswerType.NO) {
                wrong++;
            } else {
                noAnswer++;
            }
        }
        return new ResultDto(correct, wrong, noAnswer);
    }

}
