package com.imconstantine.netexam.database.dao;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.model.Student;

public interface StudentDao {

    Student insert(Student student) throws NetExamException;

    Student update(Student student) throws NetExamException;

    Student getByToken(String token) throws NetExamException;

}
