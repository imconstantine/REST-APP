package com.imconstantine.netexam.database.daoimpl;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.database.dao.LoggingDao;
import com.imconstantine.netexam.model.User;
import com.imconstantine.netexam.utils.ErrorCode;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingDaoImpl extends DaoImplBase implements LoggingDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoggingDaoImpl.class);

    @Override
    public User getUserByLogin(String login) throws NetExamException {
        LOGGER.debug("DAO get login data by login({})", login);
        User user;
        try (SqlSession sqlSession = getSession()) {
            user = getLoggingMapper(sqlSession).getUserByLogin(login);
            if (user == null) {
                LOGGER.info("Can't get login data by login({})", login);
                throw new NetExamException(ErrorCode.LOGIN_IS_NOT_EXISTING, login);
            } else {
                return user;
            }
        }
    }
}
