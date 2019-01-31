package com.imconstantine.netexam.service;

import com.imconstantine.netexam.database.dao.ExamDao;
import com.imconstantine.netexam.database.dao.QuestionDao;
import com.imconstantine.netexam.database.dao.ResultDao;
import com.imconstantine.netexam.database.dao.UserDao;
import com.imconstantine.netexam.database.daoimpl.ExamDaoImpl;
import com.imconstantine.netexam.database.daoimpl.QuestionDaoImpl;
import com.imconstantine.netexam.database.daoimpl.ResultDaoImpl;
import com.imconstantine.netexam.database.daoimpl.UserDaoImpl;
import com.imconstantine.netexam.dto.response.*;
import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.model.*;
import com.imconstantine.netexam.utils.ConverterUtils;
import com.imconstantine.netexam.utils.ErrorCode;
import com.imconstantine.netexam.utils.SessionOperationsUtils;
import com.imconstantine.netexam.dto.request.ExamDtoRequest;
import com.imconstantine.netexam.dto.request.ExamSetStateDtoRequest;
import com.imconstantine.netexam.dto.request.ExamUpdateDtoRequest;
import com.imconstantine.netexam.dto.request.QuestionDtoRequest;
import com.imconstantine.netexam.utils.validator.ExamSetStateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExamService.class);

    private final ExamDao examDao = new ExamDaoImpl();
    private final ResultDao resultDao = new ResultDaoImpl();
    private final QuestionDao questionDao = new QuestionDaoImpl();
    private final UserDao userDao = new UserDaoImpl();

    public ExamDtoResponse addExam(String token, ExamDtoRequest examDto) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        User user = userDao.getUserByToken(token);
        checkTeacherAccess(user, null);
        if (examDao.isExamWithSameNameAndSemesterExists(examDto.getName(), examDto.getSemester())) {
            throw new NetExamException(ErrorCode.EXAM_ALREADY_EXISTS);
        }
        Exam exam = ConverterUtils.convertToModel(examDto);
        examDao.insert(exam, user.getId());
        return ConverterUtils.convertToDtoResponse(exam);
    }

    public ExamDtoResponse copyExam(String token, ExamDtoRequest examDto) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        User user = userDao.getUserByToken(token);
        checkTeacherAccess(user, examDto.getSourceId());
        if (examDao.isExamWithSameNameAndSemesterExists(examDto.getName(), examDto.getSemester())) {
            throw new NetExamException(ErrorCode.EXAM_ALREADY_EXISTS);
        }
        Exam newExam = ConverterUtils.convertToModel(examDto);
        try {
            newExam.setQuestions(questionDao.getQuestionsById(examDto.getSourceId()));
        } catch (NetExamException e) {
            LOGGER.info("Getting null questions list");
        }
        examDao.batchInsert(newExam, user.getId());
        return ConverterUtils.convertToDtoResponse(newExam);
    }

    public ExamDtoResponse updateExam(String token, ExamUpdateDtoRequest examDto, Integer examId) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        User user = userDao.getUserByToken(token);
        checkTeacherAccess(user, examId);
        Exam exam = examDao.getExamBaseById(examId, user.getId());

        if (examDto.getName() != null) {
            exam.setExamName(examDto.getName());
        }
        if (examDto.getSemester() != null) {
            exam.setSemester(examDto.getSemester());
        }

        examDao.update(exam, user.getId());
        return ConverterUtils.convertToDtoResponse(exam);
    }

    public EmptyDtoResponse deleteExam(String token, Integer examId) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        User user = userDao.getUserByToken(token);
        checkTeacherAccess(user, examId);
        Exam exam = examDao.getExamBaseById(examId, user.getId());
        examDao.delete(exam);
        return new EmptyDtoResponse();
    }


    public ExamQuestionsDtoResponse setQuestions(String token, List<QuestionDtoRequest> questionDtoRequestList,
                                                 Integer examId) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        User user = userDao.getUserByToken(token);
        checkTeacherAccess(user, examId);
        Exam exam = examDao.getExamBaseById(examId, user.getId());
        examReadyCheck(exam);
        List<Question> questionList = ConverterUtils.convertToModel(questionDtoRequestList);
        questionDao.deleteAndBatchInsert(questionList, examId);
        return ConverterUtils.convertQuestionsToDtoResponse(questionList);
    }


    public ExamSetStateDtoResponse setState(String token,
                                            ExamSetStateDtoRequest stateDtoRequest,
                                            Integer examId) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        User user = userDao.getUserByToken(token);
        checkTeacherAccess(user, examId);
        Exam exam = examDao.getExamBaseById(examId, user.getId());
        examReadyCheck(exam);

        exam.setQuestions(questionDao.getQuestionsById(examId));
        exam.setQuestionsCountPerExam(stateDtoRequest.getQuestionsCountPerExam());
        exam.setTimeInMinutes(stateDtoRequest.getTimeInMinutes());
        exam.setShowDetails(stateDtoRequest.getShowDetails());
        ExamSetStateValidator.isValid(exam);

        exam.setReady(true);
        examDao.update(exam, user.getId());
        return ConverterUtils.convertToStateDtoResponse(exam);
    }

    public ExamQuestionsDtoResponse getQuestions(String token, Integer examId) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        User user = userDao.getUserByToken(token);
        checkTeacherAccess(user, examId);
        examDao.getExamBaseById(examId, user.getId());
        List<Question> questionList = questionDao.getQuestionsById(examId);
        return ConverterUtils.convertToDtoResponse(questionList);
    }

    public ExamParamDtoResponse getExamParam(String token, Integer examId) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        User user = userDao.getUserByToken(token);
        checkTeacherAccess(user, examId);
        Exam exam = examDao.getExamBaseById(examId, user.getId());
        return ConverterUtils.convertToParamDtoResponse(exam);
    }

    public ExamsParamDtoResponse getExamsParams(String token, String name, Integer semester, Boolean ready) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        User user = userDao.getUserByToken(token);
        checkTeacherAccess(user, null);
        List<Exam> exams = examDao.getAllWithParams(user.getId(), name, semester, ready);
        return ConverterUtils.convertToParamDtoResponse(exams);
    }

    public ExamStudentsResult getStudentsResult(String token, Integer id) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        User user = userDao.getUserByToken(token);
        checkTeacherAccess(user, id);
        Exam exam = examDao.getExamBaseById(id, user.getId());
        List<Group> groups = examDao.getStudentsResult(exam.getId());
        for (Group group : groups) {
            for (Student student : group.getStudents()) {
                if (student.getResult() == null) {
                    List<AnswerType> result = new ArrayList<>();
                    for (int i = 0; i < exam.getQuestionsCountPerExam(); i++) {
                        result.add(AnswerType.NO_ANSWER);
                    }
                    resultDao.updateStudentExamResult(student.getId(), exam.getId(), result);
                    student.setResult(result);
                }
            }
        }
        return ConverterUtils.convertModelToDto(exam, groups);
    }

    private void examReadyCheck(Exam exam) throws NetExamException {
        if (exam.isReady()) {
            throw new NetExamException(ErrorCode.EXAM_IS_READY);
        }
    }

    private void checkTeacherAccess(User user, Integer examId) throws NetExamException {
        if (user.getType() != UserType.TEACHER) {
            throw new NetExamException(ErrorCode.USER_IS_NOT_TEACHER);
        }
        if (examId != null) {
            if (!examDao.anyAccess(user.getId(), examId)) {
                throw new NetExamException(ErrorCode.ACCESS_IS_DENIED);
            }
        }
    }

}
