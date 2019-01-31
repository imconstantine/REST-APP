package com.imconstantine.netexam.database.daoimpl;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.database.dao.ResultDao;
import com.imconstantine.netexam.database.utils.JdbcSession;
import com.imconstantine.netexam.model.AnswerType;
import com.imconstantine.netexam.utils.ConverterUtils;
import com.imconstantine.netexam.utils.ErrorCode;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class ResultDaoImpl extends DaoImplBase implements ResultDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultDaoImpl.class);

    @Override
    public void insertStudentExam(int studentId, int examId, Date date) throws NetExamException {
        LOGGER.debug("DAO insert student passing exam studentId = {}, examId = {}, date = {}", studentId, examId, date);
        try (SqlSession sqlSession = getSession()) {
            try {
                getResultMapper(sqlSession).insertStudentExam(studentId, examId, date);
            }
            catch (PersistenceException exception) {
                LOGGER.info("Can't insert student passing exam studentId = {}, examId = {}, date = {}", studentId, examId, exception);
                sqlSession.rollback();
                throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void updateStudentExamResult(int studentId, int examId, List<AnswerType> result) throws NetExamException {
        LOGGER.debug("DAO update student exam result by {}, {}", studentId, examId);
        byte[] resultBytes = ConverterUtils.convertObjectToByteArray(result);
        JdbcSession jdbcSession = new JdbcSession();
        String insertTraineeQuery = "UPDATE student_exam SET result = ?, enddate = ? WHERE student_id = ? AND exam_id = ?";
        try (PreparedStatement stmt = jdbcSession.getConnection().prepareStatement(insertTraineeQuery)) {
            stmt.setBytes(1, resultBytes);
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis() - 1000));
            stmt.setInt(3, studentId);
            stmt.setInt(4, examId);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            LOGGER.info("Can't update student exam result ", exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        } finally {
            jdbcSession.closeConnection();
        }
    }

    @Override
    public Boolean doesExists(int studentId, int examId) {
        try (SqlSession sqlSession = getSession()) {
            return getResultMapper(sqlSession).doesExist(studentId, examId);
        }
    }

    @Override
    public Date getEndDate(int studentId, int examId) throws NetExamException {
        LOGGER.debug("DAO get end date for studentId = {}, examId = {}", studentId, examId);
        try (SqlSession sqlSession = getSession()) {
            Date date = getResultMapper(sqlSession).getEndDate(studentId, examId);
            if (date == null) {
                throw new NetExamException(ErrorCode.EXAM_NOT_TAKEN);
            } else {
                return date;
            }
        }
    }

    @Override
    public List<AnswerType> getResult(int studentId, int examId) throws NetExamException {
        LOGGER.debug("DAO select result by {}, {}", studentId, examId);
        List<AnswerType> result;
        String query = String.format("SELECT result FROM student_exam_result WHERE exam_id = %d AND student_id = %d", examId, studentId);
        JdbcSession jdbcSession = new JdbcSession();
        try (Statement statement = jdbcSession.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            resultSet.next();
            result = ConverterUtils.convertByteArrayToModel(resultSet.getBytes(1));
            return result;
        } catch (SQLException exception) {
            LOGGER.info("Can't get result ", exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        } finally {
            jdbcSession.closeConnection();
        }
    }

    @Override
    public void deleteAll() throws NetExamException {
        LOGGER.debug("DAO delete all results");
        try (SqlSession sqlSession = getSession()) {
            try {
                getResultMapper(sqlSession).deleteAll();
            } catch (PersistenceException exception) {
                LOGGER.info("Can't delete all results");
                sqlSession.rollback();
                throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
            }
            sqlSession.commit();
        }
    }

}