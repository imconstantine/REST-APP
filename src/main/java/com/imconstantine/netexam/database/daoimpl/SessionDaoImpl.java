package com.imconstantine.netexam.database.daoimpl;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.database.dao.SessionDao;
import com.imconstantine.netexam.database.utils.DBErrorCode;
import com.imconstantine.netexam.model.User;
import com.imconstantine.netexam.utils.ErrorCode;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionDaoImpl extends DaoImplBase implements SessionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDaoImpl.class);

    @Override
    public void insert(String token, User user) throws NetExamException {
        LOGGER.debug("DAO insert token({}) and user({})", token, user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSessionMapper(sqlSession).insert(token, user);
            } catch (PersistenceException exception) {
                LOGGER.info("Can't insert token({}) and user({}) ", token, user, exception);
                sqlSession.rollback();
                checkError(exception);
            }
            sqlSession.commit();
        }
    }

    @Override
    public Integer getIdByToken(String token) throws NetExamException {
        LOGGER.debug("DAO get user's id by token({})", token);
        Integer id;
        try (SqlSession sqlSession = getSession()) {
            id = getSessionMapper(sqlSession).getIdByToken(token);
            if (id == null) {
                LOGGER.info("Can't get id by token({})", token);
                throw new NetExamException(ErrorCode.TOKEN_DOES_NOT_EXIST);
            } else {
                return id;
            }
        }
    }

    @Override
    public void delete(String token) {
        LOGGER.debug("DAO delete token({})", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSessionMapper(sqlSession).delete(token);
            } catch (PersistenceException exception) {
                LOGGER.info("Can't delete session by token({}) ", token, exception);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public Boolean isTokenExist(String token) {
        LOGGER.debug("DAO is token({}) exists", token);
        try (SqlSession sqlSession = getSession()) {
            return getSessionMapper(sqlSession).isTokenExist(token);

        }
    }

    private void checkError(PersistenceException exception) throws NetExamException {
        int code = getMySQLErrorCode(exception);
        if (code == DBErrorCode.DUPLICATE_ENTRY.getCode()) {
            throw new NetExamException(ErrorCode.TOKEN_ALREADY_EXISTS);
        }
    }
}
