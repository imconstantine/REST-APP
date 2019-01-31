package com.imconstantine.netexam.database.dao;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.model.User;

public interface LoggingDao {

    User getUserByLogin(String login) throws NetExamException;

}
