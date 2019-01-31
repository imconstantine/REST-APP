package com.imconstantine.netexam.utils;

import com.imconstantine.netexam.database.dao.SessionDao;
import com.imconstantine.netexam.database.daoimpl.SessionDaoImpl;
import com.imconstantine.netexam.exception.NetExamException;

import javax.servlet.http.Cookie;
import java.util.UUID;

public class SessionOperationsUtils {

    private final static String COOKIE_NAME = "JAVASESSIONID";
    private final static SessionDao sessionDao = new SessionDaoImpl();

    public static Cookie createCookie() {
        String token = UUID.randomUUID().toString();
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setPath("/api/");
        return cookie;
    }

    public static void isTokenValid(String token) throws NetExamException {
        if (token == null) {
            throw new NetExamException(ErrorCode.TOKEN_DOES_NOT_EXIST);
        }
        if (token.length() < UUID.randomUUID().toString().length()) {
            throw new NetExamException(ErrorCode.TOKEN_DOES_NOT_EXIST);
        }
        if (sessionDao.getIdByToken(token) == null) {
            throw new NetExamException(ErrorCode.TOKEN_DOES_NOT_EXIST);
        }
    }

}
