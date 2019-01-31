package com.imconstantine.netexam.service;

import com.imconstantine.netexam.database.dao.LoggingDao;
import com.imconstantine.netexam.database.dao.SessionDao;
import com.imconstantine.netexam.database.dao.UserDao;
import com.imconstantine.netexam.database.daoimpl.LoggingDaoImpl;
import com.imconstantine.netexam.database.daoimpl.SessionDaoImpl;
import com.imconstantine.netexam.database.daoimpl.UserDaoImpl;
import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.dto.request.LoginDtoRequest;
import com.imconstantine.netexam.dto.response.UserDto;
import com.imconstantine.netexam.model.Student;
import com.imconstantine.netexam.model.Teacher;
import com.imconstantine.netexam.model.User;
import com.imconstantine.netexam.model.UserType;
import com.imconstantine.netexam.utils.ConverterUtils;
import com.imconstantine.netexam.utils.SessionOperationsUtils;
import com.imconstantine.netexam.utils.UserOperationsUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoggingService {

    private final static String COOKIE_NAME = "JAVASESSIONID";

    private LoggingDao loggingDao = new LoggingDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private SessionDao sessionDao = new SessionDaoImpl();

    public UserDto logIn(HttpServletResponse response, LoginDtoRequest loginDto) throws NetExamException {
        User tmpUser = loggingDao.getUserByLogin(loginDto.getLogin());
        loginDto.setPasswordHashKey(UserOperationsUtils.passwordToHashKey(loginDto.getPassword()));
        UserOperationsUtils.compareHashKeys(loginDto.getPasswordHashKey(), tmpUser.getPasswordHashKey());
        User user;
        if (tmpUser.getType() == UserType.TEACHER) {
            user = userDao.getTeacherById(tmpUser.getId());
        } else {
            user = userDao.getStudentById(tmpUser.getId());
        }
        Cookie cookie = SessionOperationsUtils.createCookie();
        sessionDao.insert(cookie.getValue(), user);
        response.addCookie(cookie);
        return convertModelToResponse(user);
    }

    public String logOut(HttpServletResponse response, String token) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/api/");
        response.addCookie(cookie);
        sessionDao.delete(token);
        return new String("{}");
    }

    private UserDto convertModelToResponse(User user) {
        if (user instanceof Teacher) {
            return ConverterUtils.convertToDtoResponse((Teacher) user);
        }
        return ConverterUtils.convertToDtoResponse((Student) user);
    }

}
