package com.imconstantine.netexam.database.dao;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.model.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> getQuestionsById(int examId) throws NetExamException;

    void deleteAndBatchInsert(List<Question> questions, int examId);

}
