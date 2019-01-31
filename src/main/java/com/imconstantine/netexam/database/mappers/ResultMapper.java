package com.imconstantine.netexam.database.mappers;

import org.apache.ibatis.annotations.*;

import java.util.Date;

public interface ResultMapper {

    @Insert("INSERT INTO student_exam (student_id, exam_id, enddate) VALUES (#{studentId}, #{examId}, #{date})")
    void insertStudentExam(@Param("studentId") int studentId, @Param("examId") int examId, @Param("date") Date date);

    @Select("SELECT EXISTS(SELECT 1 FROM student_exam WHERE student_id = #{studentId} AND exam_id = #{examId})")
    Boolean doesExist(@Param("studentId") int studentId, @Param("examId") int examId);

    @Select("SELECT enddate FROM student_exam WHERE student_id = #{studentId} AND exam_id = #{examId}")
    Date getEndDate(@Param("studentId") int studentId, @Param("examId") int examId);

    @Delete("DELETE FROM student_exam")
    void deleteAll();

}
