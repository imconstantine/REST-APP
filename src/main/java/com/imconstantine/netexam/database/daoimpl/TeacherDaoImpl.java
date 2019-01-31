package com.imconstantine.netexam.database.daoimpl;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.database.dao.TeacherDao;
import com.imconstantine.netexam.database.utils.DBErrorCode;
import com.imconstantine.netexam.model.Teacher;
import com.imconstantine.netexam.utils.ErrorCode;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeacherDaoImpl extends DaoImplBase implements TeacherDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(TeacherDaoImpl.class);

    @Override
    public Teacher insert(Teacher teacher) throws NetExamException {
        LOGGER.debug("DAO insert teacher {}", teacher);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(teacher);
                getTeacherMapper(sqlSession).insert(teacher);
            } catch (PersistenceException exception) {
                LOGGER.info("Can't insert teacher {}, ", teacher, exception.getMessage());
                sqlSession.rollback();
                checkError(exception, teacher);
            }
            sqlSession.commit();
        }
        return teacher;
    }

    @Override
    public Teacher update(Teacher teacher) throws NetExamException {
        LOGGER.debug("DAO update teacher {} ", teacher);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).update(teacher);
                getTeacherMapper(sqlSession).update(teacher);
            } catch (PersistenceException exception) {
                LOGGER.info("Can't update teacher {}, ", teacher);
                sqlSession.rollback();
                checkError(exception, teacher);
            }
            sqlSession.commit();
        }
        return teacher;
    }

    @Override
    public Teacher getByToken(String token) throws NetExamException {
        LOGGER.debug("DAO select teacher by token = {}", token);
        Teacher teacher;
        try (SqlSession sqlSession = getSession()) {
            teacher = getTeacherMapper(sqlSession).getByToken(token);
            if (teacher == null) {
                LOGGER.info("Can't get teacher by token = {}", token);
                throw new NetExamException(ErrorCode.NOT_FOUND);
            } else {
                return teacher;
            }
        } catch (PersistenceException exception) {
            LOGGER.info("Can't get teacher by token = {} ", token, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    private void checkError(PersistenceException exception, Teacher teacher) throws NetExamException {
        int code = getMySQLErrorCode(exception);
        if (code == DBErrorCode.DUPLICATE_ENTRY.getCode()) {
            throw new NetExamException(ErrorCode.LOGIN_ALREADY_EXISTS, teacher.getLogin());
        }
    }

}
