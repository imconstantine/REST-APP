package com.imconstantine.netexam.service;

import com.imconstantine.netexam.database.dao.SessionDao;
import com.imconstantine.netexam.database.dao.StudentDao;
import com.imconstantine.netexam.database.daoimpl.SessionDaoImpl;
import com.imconstantine.netexam.database.daoimpl.StudentDaoImpl;
import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.dto.request.StudentSignUpDtoRequest;
import com.imconstantine.netexam.dto.request.StudentUpdateDtoRequest;
import com.imconstantine.netexam.dto.response.StudentDtoResponse;
import com.imconstantine.netexam.model.Student;
import com.imconstantine.netexam.utils.ConverterUtils;
import com.imconstantine.netexam.utils.SessionOperationsUtils;
import com.imconstantine.netexam.utils.UserOperationsUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class StudentService {

    private StudentDao studentDao = new StudentDaoImpl();
    private SessionDao sessionDao = new SessionDaoImpl();

    public StudentDtoResponse signUp(HttpServletResponse response, StudentSignUpDtoRequest studentDto) throws NetExamException {
        studentDto.setHashKey(UserOperationsUtils.passwordToHashKey(studentDto.getPassword()));
        Student student = ConverterUtils.convertToModel(studentDto);
        studentDao.insert(student);
        Cookie cookie = SessionOperationsUtils.createCookie();
        sessionDao.insert(cookie.getValue(), student);
        response.addCookie(cookie);
        return ConverterUtils.convertToDtoResponse(student);
    }

    public StudentDtoResponse update(String token, StudentUpdateDtoRequest studentDto) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        Student student = studentDao.getByToken(token);
        if (studentDto.getOldPassword() != null) {
            studentDto.setPasswordHashKey(UserOperationsUtils.passwordToHashKey(studentDto.getOldPassword()));
        }
        UserOperationsUtils.compareHashKeys(student.getPasswordHashKey(), studentDto.getPasswordHashKey());

        if (studentDto.getNewPassword() != null) {
            student.setPasswordHashKey(UserOperationsUtils.passwordToHashKey(studentDto.getNewPassword()));
        }
        if (studentDto.getFirstName() != null) {
            student.setFirstName(studentDto.getFirstName());
        }
        if (studentDto.getLastName() != null) {
            student.setLastName(studentDto.getLastName());
        }
        if (studentDto.getPatronymic() != null) {
            student.setPatronymic(studentDto.getPatronymic());
        }
        if (studentDto.getGroup() != null) {
            student.setGroup(studentDto.getGroup());
        }
        if (studentDto.getSemester() != null) {
            student.setSemester(studentDto.getSemester());
        }

        studentDao.update(student);
        return ConverterUtils.convertToDtoResponse(student);
    }

}
