package com.imconstantine.netexam.utils.validator;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.model.Answer;
import com.imconstantine.netexam.model.Exam;
import com.imconstantine.netexam.model.Question;
import com.imconstantine.netexam.utils.ErrorCode;

import java.util.HashSet;
import java.util.Set;

public class ExamSetStateValidator   {

    public static boolean isValid(Exam exam) throws NetExamException {
        NetExamException examException = new NetExamException();
        int counterAllNumbersIsZero = 0;
        if (exam.getQuestions().size() < exam.getQuestionsCountPerExam()) {
            examException.add(ErrorCode.EXAM_QUESTIONS_LESS_THAN_QUESTIONS_COUNT_EXAM);
        }
        for (Question question : exam.getQuestions()) {
            if (question.getContent() == null) {
                examException.add(ErrorCode.EXAM_HAS_EMPTY_QUESTION);
            }
        }
        for (Question question : exam.getQuestions()) {
            if (question.getAnswerList().size() < Constants.MIN_ANSWERS) {
                examException.add(ErrorCode.QUESTION_HAS_LESS_ANSWERS);
            }
        }
        for (Question question : exam.getQuestions()) {
            if (question.getCorrect() == null || (question.getCorrect() < 1 || question.getCorrect() > question.getAnswerList().size())) {
                examException.add(ErrorCode.QUESTION_INVALID_CORRECT);
            }
        }
        for (Question question : exam.getQuestions()) {
            for (Answer answer : question.getAnswerList()) {
                if (answer == null || answer.getBody().equals("")) {
                    examException.add(ErrorCode.ANSWER_IS_NULL);
                }
            }
        }
        for (Question question : exam.getQuestions()) {
            if (question.getNumber() == 0) {
                counterAllNumbersIsZero++;
            } else {
                if (counterAllNumbersIsZero > 0) {
                    examException.add(ErrorCode.QUESTIONS_NUMBERS_NOT_VALID);
                    break;
                }
            }
        }
        if (counterAllNumbersIsZero == 0) {
            Set<Integer> counterDifferentNumbers = new HashSet<>();
            for (Question question : exam.getQuestions()) {
                counterDifferentNumbers.add(question.getNumber());
            }
            if (counterDifferentNumbers.size() != exam.getQuestions().size()) {
                examException.add(ErrorCode.QUESTIONS_NUMBERS_NOT_VALID);
            }
        }
        if (exam.getQuestionsCountPerExam() < Constants.MIN_QUESTIONS_COUNT_PER_EXAM) {
            examException.add(ErrorCode.EXAM_QUESTIONS_COUNT_LESS_THAN_ALLOWED);
        }
        if (exam.getTimeInMinutes() < Constants.MIN_TIME) {
            examException.add(ErrorCode.EXAM_TIME_LESS_THAN_ALLOWED);
        }
        if (examException.getExceptionList().size() != 0) {
            throw examException;
        }
        return true;
    }
}
