package com.imconstantine.netexam.database.daoimpl;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.database.dao.StudentDao;
import com.imconstantine.netexam.database.utils.DBErrorCode;
import com.imconstantine.netexam.model.Student;
import com.imconstantine.netexam.utils.ErrorCode;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentDaoImpl extends DaoImplBase implements StudentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDaoImpl.class);

    @Override
    public Student insert(Student student) throws NetExamException {
        LOGGER.debug("DAO insert student {}", student);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(student);
                getStudentMapper(sqlSession).insert(student);
            } catch (PersistenceException exception) {
                LOGGER.info("Can't insert student {}, ", student, exception.getMessage());
                sqlSession.rollback();
                checkError(exception, student);
            }
            sqlSession.commit();
        }
        return student;
    }

    @Override
    public Student update(Student student) throws NetExamException {
        LOGGER.debug("DAO update student {}", student);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).update(student);
                getStudentMapper(sqlSession).update(student);
            } catch (PersistenceException exception) {
                LOGGER.info("Can't update student {}, ", student);
                sqlSession.rollback();
                checkError(exception, student);
            }
            sqlSession.commit();
        }
        return student;
    }

    @Override
    public Student getByToken(String token) throws NetExamException {
        LOGGER.debug("DAO select student by token = {}", token);
        Student student;
        try (SqlSession sqlSession = getSession()) {
            student = getStudentMapper(sqlSession).getByToken(token);
            if (student == null) {
                LOGGER.info("Can't get student by token = {}", token);
                throw new NetExamException(ErrorCode.NOT_FOUND);
            } else {
                return student;
            }
        } catch (PersistenceException exception) {
            LOGGER.info("Can't get student by token = {} ", token, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    private void checkError(PersistenceException exception, Student student) throws NetExamException {
        int code = getMySQLErrorCode(exception);
        if (code == DBErrorCode.DUPLICATE_ENTRY.getCode()) {
            throw new NetExamException(ErrorCode.LOGIN_ALREADY_EXISTS, student.getLogin());
        }
    }

}
