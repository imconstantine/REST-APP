package com.imconstantine.netexam.database.daoimpl;

import com.imconstantine.netexam.database.mappers.*;
import com.imconstantine.netexam.database.utils.MyBatisUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;

public class DaoImplBase {

    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected TeacherMapper getTeacherMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(TeacherMapper.class);
    }

    protected StudentMapper getStudentMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(StudentMapper.class);
    }

    protected UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }

    protected SessionMapper getSessionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SessionMapper.class);
    }

    protected LoggingMapper getLoggingMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(LoggingMapper.class);
    }

    protected ExamMapper getExamMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ExamMapper.class);
    }

    protected QuestionMapper getQuestionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(QuestionMapper.class);
    }

    protected AnswerMapper getAnswerMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(AnswerMapper.class);
    }

    protected ResultMapper getResultMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ResultMapper.class);
    }

    protected int getMySQLErrorCode(PersistenceException exception) {
        return ((SQLException)exception.getCause()).getErrorCode();
    }

}
