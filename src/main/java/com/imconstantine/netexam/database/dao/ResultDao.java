package com.imconstantine.netexam.database.dao;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.model.AnswerType;

import java.util.Date;
import java.util.List;

public interface ResultDao {

    void insertStudentExam(int studentId, int examId, Date date) throws NetExamException;

    void updateStudentExamResult(int studentId, int examId, List<AnswerType> result) throws NetExamException;

    Boolean doesExists(int studentId, int examId);

    Date getEndDate(int studentId, int examId) throws NetExamException;

    List<AnswerType> getResult(int studentId, int examId) throws NetExamException;

    void deleteAll() throws NetExamException;
}
