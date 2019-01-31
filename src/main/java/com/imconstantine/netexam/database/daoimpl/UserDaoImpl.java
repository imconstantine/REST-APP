package com.imconstantine.netexam.database.daoimpl;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.database.dao.UserDao;
import com.imconstantine.netexam.model.Student;
import com.imconstantine.netexam.model.Teacher;
import com.imconstantine.netexam.model.User;
import com.imconstantine.netexam.model.UserType;
import com.imconstantine.netexam.utils.ErrorCode;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDaoImpl extends DaoImplBase implements UserDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User getUserByToken(String token) {
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getUserByToken(token);
        }
    }

    @Override
    public UserType getUserTypeByToken(String token) throws NetExamException {
        LOGGER.debug("DAO select user type by token = {}", token);
        UserType userType;
        try (SqlSession sqlSession = getSession()) {
            userType = getUserMapper(sqlSession).getUserTypeByToken(token);
            if (userType == null) {
                LOGGER.info("Can't get user type by token = {}", token);
                throw new NetExamException(ErrorCode.TOKEN_DOES_NOT_EXIST);
            } else {
                return userType;
            }
        } catch (PersistenceException exception) {
            LOGGER.info("Can't get user type by token = {}", token, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public Teacher getTeacherById(int id) throws NetExamException {
        LOGGER.debug("DAO select teacher by id = {}", id);
        Teacher teacher;
        try (SqlSession sqlSession = getSession()) {
            teacher = getUserMapper(sqlSession).getTeacherById(id);
            if (teacher == null) {
                LOGGER.info("Can't get Teacher by id = {}", id);
                throw new NetExamException(ErrorCode.ID_DOES_NOT_EXIST, id);
            } else {
                return teacher;
            }
        }
    }

    @Override
    public Student getStudentById(int id) throws NetExamException {
        LOGGER.debug("DAO select student by id = {}", id);
        Student student;
        try (SqlSession sqlSession = getSession()) {
            student = getUserMapper(sqlSession).getStudentById(id);
            if (student == null) {
                LOGGER.info("Can't get Student by id = {}", id);
                throw new NetExamException(ErrorCode.ID_DOES_NOT_EXIST, id);
            } else {
                return student;
            }
        }
    }

    @Override
    public void deleteAll() throws NetExamException {
        LOGGER.debug("DAO delete all users");
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).deleteAll();
            } catch (PersistenceException exception) {
                LOGGER.info("Can't delete all users");
                sqlSession.rollback();
                throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
            }
            sqlSession.commit();
        }
    }

}
