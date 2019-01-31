package com.imconstantine.netexam.database.dao;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.model.Student;
import com.imconstantine.netexam.model.Teacher;
import com.imconstantine.netexam.model.User;
import com.imconstantine.netexam.model.UserType;

public interface UserDao {

    User getUserByToken(String token);

    UserType getUserTypeByToken(String token) throws NetExamException;

    Teacher getTeacherById(int id) throws NetExamException;

    Student getStudentById(int id) throws NetExamException;

    void deleteAll() throws NetExamException;

}
