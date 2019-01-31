package com.imconstantine.netexam.database.daoimpl;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.database.dao.QuestionDao;
import com.imconstantine.netexam.model.Question;
import com.imconstantine.netexam.utils.ErrorCode;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QuestionDaoImpl extends DaoImplBase implements QuestionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionDaoImpl.class);

    @Override
    public List<Question> getQuestionsById(int examId) throws NetExamException {
        LOGGER.debug("DAO select questions by examId = {}", examId);
        List<Question> questionList;
        try (SqlSession sqlSession = getSession()) {
            questionList = getQuestionMapper(sqlSession).getQuestionsById(examId);
            if (questionList == null || questionList.isEmpty()) {
                LOGGER.info("Can't get questions by examId = {}", examId);
                throw new NetExamException(ErrorCode.QUESTIONS_IS_NOT_EXIST);
            } else {
                return questionList;
            }
        } catch (PersistenceException exception) {
            LOGGER.info("Can't get questions by examId = {} ", examId, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public void deleteAndBatchInsert(List<Question> questions, int examId) {
        LOGGER.debug("DAO delete and batch insert questions {}", questions);
        try (SqlSession sqlSession = getSession()) {
            try {
                getQuestionMapper(sqlSession).deleteQuestions(examId);
                getQuestionMapper(sqlSession).batchInsert(questions, examId);
                for (Question question : questions) {
                    if (question.getAnswerList() != null && question.getAnswerList().size() != 0) {
                        getAnswerMapper(sqlSession).batchInsert(question.getAnswerList(), question.getId());
                    }
                }
            } catch (PersistenceException exception) {
                LOGGER.info("Can't delete and batch insert questions {} ", questions, exception.getMessage());
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }
}
