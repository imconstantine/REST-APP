package com.imconstantine.netexam.database.dao;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.model.Teacher;

public interface TeacherDao {

    Teacher insert(Teacher teacher) throws NetExamException;

    Teacher update(Teacher teacher) throws NetExamException;

    Teacher getByToken(String token) throws NetExamException;

}
