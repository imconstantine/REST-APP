package com.imconstantine.netexam.service;

import com.imconstantine.netexam.database.dao.SessionDao;
import com.imconstantine.netexam.database.dao.TeacherDao;
import com.imconstantine.netexam.database.daoimpl.SessionDaoImpl;
import com.imconstantine.netexam.database.daoimpl.TeacherDaoImpl;
import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.dto.request.TeacherSignUpDtoRequest;
import com.imconstantine.netexam.dto.request.TeacherUpdateDtoRequest;
import com.imconstantine.netexam.dto.response.TeacherDtoResponse;
import com.imconstantine.netexam.model.Teacher;
import com.imconstantine.netexam.utils.ConverterUtils;
import com.imconstantine.netexam.utils.SessionOperationsUtils;
import com.imconstantine.netexam.utils.UserOperationsUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class TeacherService {


    private TeacherDao teacherDao = new TeacherDaoImpl();
    private SessionDao sessionDao = new SessionDaoImpl();

    public TeacherDtoResponse signUp(HttpServletResponse response, TeacherSignUpDtoRequest teacherDto) throws NetExamException {
        teacherDto.setPasswordHashKey(UserOperationsUtils.passwordToHashKey(teacherDto.getPassword()));
        Teacher teacher = ConverterUtils.convertToModel(teacherDto);
        teacherDao.insert(teacher);
        Cookie cookie = SessionOperationsUtils.createCookie();
        sessionDao.insert(cookie.getValue(), teacher);
        response.addCookie(cookie);
        return ConverterUtils.convertToDtoResponse(teacher);
    }

    public TeacherDtoResponse update(String token, TeacherUpdateDtoRequest teacherDto) throws NetExamException {
        SessionOperationsUtils.isTokenValid(token);
        Teacher teacher = teacherDao.getByToken(token);
        if (teacherDto.getOldPassword() != null) {
            teacherDto.setPasswordHashKey(UserOperationsUtils.passwordToHashKey(teacherDto.getOldPassword()));
        }
        UserOperationsUtils.compareHashKeys(teacher.getPasswordHashKey(), teacherDto.getPasswordHashKey());

        if (teacherDto.getNewPassword() != null) {
            teacher.setPasswordHashKey(UserOperationsUtils.passwordToHashKey(teacherDto.getNewPassword()));
        }
        if (teacherDto.getFirstName() != null) {
            teacher.setFirstName(teacherDto.getFirstName());
        }
        if (teacherDto.getLastName() != null) {
            teacher.setLastName(teacherDto.getLastName());
        }
        if (teacherDto.getPatronymic() != null) {
            teacher.setPatronymic(teacherDto.getPatronymic());
        }
        if (teacherDto.getDepartment() != null) {
            teacher.setDepartment(teacherDto.getDepartment());
        }
        if (teacherDto.getPosition() != null) {
            teacher.setPosition(teacherDto.getPosition());
        }

        teacherDao.update(teacher);
        return ConverterUtils.convertToDtoResponse(teacher);
    }

}
