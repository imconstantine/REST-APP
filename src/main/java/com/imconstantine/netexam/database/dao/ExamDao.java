package com.imconstantine.netexam.database.dao;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.model.Exam;
import com.imconstantine.netexam.model.ExamInfo;
import com.imconstantine.netexam.model.Group;

import java.util.List;

public interface ExamDao {

    Exam insert(Exam exam, int teacherId) throws NetExamException;

    Exam getExamBaseById(int examId, Integer teacherId) throws NetExamException;

    List<Exam> getAllWithParams(int teacherId, String name, Integer semester, Boolean ready) throws NetExamException;

    List<ExamInfo> getAllExamInfo(int semester) throws NetExamException;

    List<ExamInfo> getPassedExamInfo(int studentId) throws NetExamException;

    List<ExamInfo> getCurrentExamInfo(int studentId) throws NetExamException;

    List<ExamInfo> getRemainingExamInfo(int studentId) throws NetExamException;

    List<Group> getStudentsResult(int examId) throws NetExamException;

    List<ExamInfo> getStudentResults(int studentId) throws NetExamException;

    Integer getTeacherIdByExamId(int examId) throws NetExamException;

    void update(Exam exam, int teacherId) throws NetExamException;

    void delete(Exam exam) throws NetExamException;

    Boolean isExamWithSameNameAndSemesterExists(String name, int semester);

    Boolean anyAccess(int teacherId, int examId);

    void batchInsert(Exam exam, int teacherId);

}
