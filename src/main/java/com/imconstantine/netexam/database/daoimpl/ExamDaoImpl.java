package com.imconstantine.netexam.database.daoimpl;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.database.dao.ExamDao;
import com.imconstantine.netexam.model.Exam;
import com.imconstantine.netexam.model.ExamInfo;
import com.imconstantine.netexam.model.Group;
import com.imconstantine.netexam.utils.ErrorCode;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExamDaoImpl extends DaoImplBase implements ExamDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamDaoImpl.class);

    @Override
    public Exam insert(Exam exam, int teacherId) {
        LOGGER.debug("DAO insert Exam by teacher(id={}) {}", teacherId, exam);
        try (SqlSession sqlSession = getSession()) {
            try {
                getExamMapper(sqlSession).insert(exam, teacherId);
            } catch (PersistenceException exception) {
                LOGGER.info("Can't insert exam {} ", exam, exception);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
        return exam;
    }

    @Override
    public Exam getExamBaseById(int examId, Integer teacherId) throws NetExamException {
        LOGGER.debug("DAO select Exam by examId = {} and teacherId = {}", examId, teacherId);
        Exam exam;
        try (SqlSession sqlSession = getSession()) {
            exam = getExamMapper(sqlSession).getExamBaseById(examId, teacherId);
            if (exam == null) {
                LOGGER.info("Can't get Exam by examId = {} and teacherId = {}", examId, teacherId);
                throw new NetExamException(ErrorCode.EXAM_IS_NOT_EXIST, examId);
            } else {
                return exam;
            }
        } catch (PersistenceException exception) {
            LOGGER.info("Can't get Exam by examId = {} and teacherId = {} ", examId, teacherId, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public List<Exam> getAllWithParams(int teacherId, String name, Integer semester, Boolean ready) throws NetExamException {
        LOGGER.debug("DAO select list of Exam by teacherId = {}, name = {}, semester = {}, ready = {}", teacherId, name, semester, ready);
        List<Exam> exams;
        try (SqlSession sqlSession = getSession()) {
            exams = getExamMapper(sqlSession).getAllWithParams(teacherId, name, semester, ready);
            if (exams == null) {
                LOGGER.info("Can't get Exam by teacherId = {}, name = {}, semester = {}, ready = {}", teacherId, name, semester, ready);
                throw new NetExamException(ErrorCode.EXAM_IS_NOT_EXIST);
            } else {
                return exams;
            }
        } catch (PersistenceException exception) {
            LOGGER.info("Can't get Exam by teacherId = {}, name = {}, semester = {}, ready = {} ", teacherId, name, semester, ready, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public List<ExamInfo> getAllExamInfo(int semester) throws NetExamException {
        LOGGER.debug("DAO select ExamInfo by semester = {}", semester);
        List<ExamInfo> examInfoList;
        try (SqlSession sqlSession = getSession()) {
            examInfoList = getExamMapper(sqlSession).getAllExamInfo(semester);
            if (examInfoList == null) {
                LOGGER.info("Can't get Exam by semester = {} ", semester);
                throw new NetExamException(ErrorCode.EXAM_IS_NOT_EXIST);
            } else {
                return examInfoList;
            }
        } catch (PersistenceException exception) {
            LOGGER.info("Can't get Exam by semester = {} ", semester, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public List<ExamInfo> getPassedExamInfo(int studentId) throws NetExamException {
        LOGGER.debug("DAO select passed ExamInfo by studentId = {}", studentId);
        List<ExamInfo> examInfoList;
        try (SqlSession sqlSession = getSession()) {
            examInfoList = getExamMapper(sqlSession).getPassedExamInfo(studentId);
            if (examInfoList == null) {
                LOGGER.info("Can't get Exam by studentId = {}", studentId);
                throw new NetExamException(ErrorCode.EXAM_IS_NOT_EXIST);
            } else {
                return examInfoList;
            }
        } catch (PersistenceException exception) {
            LOGGER.info("Can't get Exam by studentId = {}", studentId, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public List<ExamInfo> getCurrentExamInfo(int studentId) throws NetExamException {
        LOGGER.debug("DAO select current ExamInfo by studentId = {}", studentId);
        List<ExamInfo> examInfoList;
        try (SqlSession sqlSession = getSession()) {
            examInfoList = getExamMapper(sqlSession).getCurrentExamInfo(studentId);
            if (examInfoList == null) {
                LOGGER.info("Can't get Exam by studentId = {} ", studentId);
                throw new NetExamException(ErrorCode.EXAM_IS_NOT_EXIST);
            } else {
                return examInfoList;
            }
        } catch (PersistenceException exception) {
            LOGGER.info("Can't get Exam by studentId = {} ", studentId, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public List<ExamInfo> getRemainingExamInfo(int studentId) throws NetExamException {
        LOGGER.debug("DAO select remaining ExamInfo by studentId = {}", studentId);
        List<ExamInfo> examInfoList;
        try (SqlSession sqlSession = getSession()) {
            examInfoList = getExamMapper(sqlSession).getRemainingExamInfo(studentId);
            if (examInfoList == null) {
                LOGGER.info("Can't get Exam by studentId = {} ", studentId);
                throw new NetExamException(ErrorCode.EXAM_IS_NOT_EXIST);
            } else {
                return examInfoList;
            }
        } catch (PersistenceException exception) {
            LOGGER.info("Can't get Exam by studentId = {} ", studentId, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public List<Group> getStudentsResult(int examId) throws NetExamException {
        LOGGER.debug("DAO select student's results by examId = {}", examId);
        List<Group> groups;
        try (SqlSession sqlSession = getSession()) {
            groups = getExamMapper(sqlSession).getStudentsResult(examId);
            if (groups == null) {
                LOGGER.info("Can't get student's results by examId = {}", examId);
                throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
            } else {
                return groups;
            }
        } catch (PersistenceException exception) {
            LOGGER.info("Can't get student's results by examId = {} ", examId, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public List<ExamInfo> getStudentResults(int studentId) throws NetExamException {
        LOGGER.debug("DAO select student results by studentId = {}", studentId);
        List<ExamInfo> examInfoList;
        try (SqlSession sqlSession = getSession()) {
            examInfoList = getExamMapper(sqlSession).getStudentResults(studentId);
            if (examInfoList == null) {
                LOGGER.info("Can't get students results");
                throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
            } else {
                return examInfoList;
            }
        } catch (PersistenceException exception) {
            LOGGER.info("Can't get student results by studentId = {} ", studentId, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }


    @Override
    public Integer getTeacherIdByExamId(int examId) throws NetExamException {
        LOGGER.debug("DAO select teacherId by examId = {}", examId);
        try (SqlSession sqlSession = getSession()) {
            Integer teacherId = getExamMapper(sqlSession).getTeacherIdByExamId(examId);
            if (teacherId == null) {
                LOGGER.info("Can't get teacherId by examId = {}", examId);
                throw new NetExamException(ErrorCode.EXAM_IS_NOT_EXIST);
            } else {
                return teacherId;
            }
        } catch (PersistenceException exception) {
            LOGGER.info("Can't get teacherId by examId = {} ", examId, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public void update(Exam exam, int teacherId) throws NetExamException {
        LOGGER.debug("DAO update Exam {}", exam);
        try (SqlSession sqlSession = getSession()) {
            try {
                getExamMapper(sqlSession).update(exam, teacherId);
            } catch (PersistenceException exception) {
                LOGGER.info("Can't update Exam {} ", exam, exception);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public void delete(Exam exam) throws NetExamException {
        LOGGER.debug("DAO delete Exam {}", exam);
        try (SqlSession sqlSession = getSession()) {
            try {
                getExamMapper(sqlSession).delete(exam);
            } catch (PersistenceException exception) {
                LOGGER.info("Can't delete Exam {} ", exam, exception);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public Boolean isExamWithSameNameAndSemesterExists(String name, int semester) {
        try (SqlSession sqlSession = getSession()) {
            return getExamMapper(sqlSession).isExamWithSameNameAndSemesterExists(name, semester);
        }
    }

    @Override
    public Boolean anyAccess(int teacherId, int examId) {
        try (SqlSession sqlSession = getSession()) {
            return getExamMapper(sqlSession).anyAccess(teacherId, examId);
        }
    }

    @Override
    public void batchInsert(Exam exam, int teacherId) {
        LOGGER.debug("DAO insert Exam by teacher(id={}) ", teacherId);
        try (SqlSession sqlSession = getSession()) {
            try {
                getExamMapper(sqlSession).insert(exam, teacherId);
                getQuestionMapper(sqlSession).batchInsert(exam.getQuestions(), exam.getId());
            } catch (PersistenceException exception) {
                LOGGER.info("Can't insert exam and questions {} ", exam, exception);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

}
