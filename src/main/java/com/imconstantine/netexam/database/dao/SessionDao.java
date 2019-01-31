package com.imconstantine.netexam.database.dao;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.model.User;

public interface SessionDao {

    void insert(String token, User user) throws NetExamException;

    Integer getIdByToken(String token) throws NetExamException;

    void delete(String token);

    Boolean isTokenExist(String token);

}
