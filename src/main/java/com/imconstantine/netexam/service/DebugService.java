package com.imconstantine.netexam.service;

import com.imconstantine.netexam.database.daoimpl.ResultDaoImpl;
import com.imconstantine.netexam.database.daoimpl.UserDaoImpl;
import com.imconstantine.netexam.exception.NetExamException;
import org.springframework.stereotype.Service;

@Service
public class DebugService {

    public String clearDateBase() throws NetExamException {
        new UserDaoImpl().deleteAll();
        new ResultDaoImpl().deleteAll();
        return new String("");
    }

}
