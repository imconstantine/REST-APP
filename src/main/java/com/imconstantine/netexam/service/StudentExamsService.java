package com.imconstantine.netexam.service;

import com.imconstantine.netexam.database.dao.*;
import com.imconstantine.netexam.database.daoimpl.*;
import com.imconstantine.netexam.dto.response.ExamForSolutionDtoResponse;
import com.imconstantine.netexam.dto.response.ExamsAndTeachersDtoResponse;
import com.imconstantine.netexam.dto.response.StudentExamResultDtoResponse;
import com.imconstantine.netexam.dto.response.StudentExamResultListDtoResponse;
import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.model.*;
import com.imconstantine.netexam.utils.ConverterUtils;
import com.imconstantine.netexam.utils.ErrorCode;
import com.imconstantine.netexam.utils.SessionOperationsUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StudentExamsService {

    private final byte SECONDS_IN_MINUTE = 60;
    private final int MILLIS_IN_SECOND = 1000;

    private final StudentDao studentDao = new StudentDaoImpl();
    private final UserDao userDao = new UserDaoImpl();
    private final ExamDao examDao = new ExamDaoImpl();
    private final ResultDao resultDao = new ResultDaoImpl();
    private final QuestionDao questionDao = new QuestionDaoImpl();

    public ExamsAndTeachersDtoResponse getExams(String token, String filterValue) throws NetExamException {
        Filter filter = Filter.getFilter(filterValue);
        SessionOperationsUtils.isTokenValid(token);
        Student student = studentDao.getByToken(token);
        List<ExamInfo> examInfoList = new ArrayList<>();
        if (filter == Filter.ALL) {
            examInfoList = examDao.getAllExamInfo(student.getSemester());
        } else if (filter == Filter.PASSED) {
            examInfoList = examDao.getPassedExamInfo(student.getId());
        } else if (filter == Filter.CURRENT) {
            examInfoList = examDao.getCurrentExamInfo(student.getId());
        } else if (filter == Filter.REMAINING) {
            examInfoList = examDao.getRemainingExamInfo(student.getId());
        }
        return ConverterUtils.converToDtoResponse(examInfoList);
    }

    public ExamForSolutionDtoResponse getExamForSolution(String token, Integer examId) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        Student student = studentDao.getByToken(token);
        Teacher teacher = userDao.getTeacherById(examDao.getTeacherIdByExamId(examId));
        Exam exam = examDao.getExamBaseById(examId, teacher.getId());
        if (exam.getSemester() != student.getSemester() || !exam.isReady()) {
            throw new NetExamException(ErrorCode.NO_READY_EXAM_FOR_THIS_SEMESTER);
        }
        exam.setQuestions(questionDao.getQuestionsById(examId));
        try {
            resultDao.getEndDate(student.getId(), exam.getId());
        } catch (NetExamException exception) {
            if (exception.getExceptionList().get(0).getErrorCode() == ErrorCode.EXAM_NOT_TAKEN) {
                Date endExamDate = new Date(System.currentTimeMillis() + exam.getTimeInMinutes() * SECONDS_IN_MINUTE * MILLIS_IN_SECOND);
                resultDao.insertStudentExam(student.getId(), exam.getId(), endExamDate);
                return ConverterUtils.convertToResponse(exam, teacher);
            }
        }
        return ConverterUtils.convertToResponse(exam, teacher);
    }

    public StudentExamResultDtoResponse setSolution(String token, List<Integer> solution, Integer id) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        Student student = studentDao.getByToken(token);
        Exam exam = examDao.getExamBaseById(id, null);
        if (solution.size() < exam.getQuestionsCountPerExam()) {
            throw new NetExamException(ErrorCode.INCOMPLETE_SOLUTION_SET);
        }
        final int size = solution.size();
        for (int i = 0; i < exam.getQuestionsCountPerExam() - size; i++) {
            solution.add(null);
        }
        if (!new Date().before(resultDao.getEndDate(student.getId(), exam.getId()))) {
            throw new NetExamException(ErrorCode.EXAM_TIME_IS_UP);
        }
        exam.setQuestions(questionDao.getQuestionsById(id));
        Integer answer;
        List<AnswerType> results = new ArrayList<>();
        int correctCounter = 0;
        for (int i = 0; i < solution.size(); i++) {
            answer = solution.get(i);
            if (answer == null) {
                results.add(AnswerType.NO_ANSWER);
                continue;
            }
            if (solution.get(i).equals(exam.getQuestions().get(i).getCorrect())) {
                results.add(AnswerType.YES);
                correctCounter++;
            } else {
                results.add(AnswerType.NO);
            }
        }
        resultDao.updateStudentExamResult(student.getId(), exam.getId(), results);
        return ConverterUtils.convertToDtoResponse(exam, correctCounter, results);
    }

    public StudentExamResultListDtoResponse getResults(String token) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        Student student = studentDao.getByToken(token);
        List<ExamInfo> examResults = examDao.getStudentResults(student.getId());
        for (ExamInfo examInfo : examResults) {
            if (examInfo.getResult() == null) {
                List<AnswerType> result = new ArrayList<>();
                for (int i = 0; i < examInfo.getQuestionsCountPerExam(); i++) {
                    result.add(AnswerType.NO_ANSWER);
                }
                resultDao.updateStudentExamResult(student.getId(), examInfo.getId(), result);
                examInfo.setResult(result);
            }
        }
        return ConverterUtils.convertModelToDtoResponse(examResults);
    }
}
