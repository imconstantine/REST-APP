package com.imconstantine.netexam.database.mappers;

import com.imconstantine.netexam.model.Exam;
import com.imconstantine.netexam.model.ExamInfo;
import com.imconstantine.netexam.model.Group;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ExamMapper {

    @Insert("INSERT INTO exam (teacher_id, name, semester) VALUES (#{teacherId}, #{exam.examName}, #{exam.semester})")
    @Options(useGeneratedKeys = true, keyProperty = "exam.id")
    Integer insert(@Param("exam") Exam exam, @Param("teacherId") int teacherId);

    @Select("<script>" +
            "SELECT id, name AS examName, semester, ready, time AS timeInMinutes, count AS questionsCountPerExam," +
            " details AS showDetails FROM exam " +
            "<where>" +
            " id = #{examId}" +
            "<if test='teacherId != null'> AND teacher_id = #{teacherId}</if>" +
            "</where>" +
            "</script>")
    Exam getExamBaseById(@Param("examId") int examId, @Param("teacherId") Integer teacherId);

    @Select("<script>" +
            "SELECT id, name AS examName, semester, ready, time AS timeInMinutes, " +
            "count AS questionsCountPerExam, details AS showDetails FROM exam" +
            "<where>" +
            " teacher_id = #{teacherId}" +
            "<if test='name != null'> AND name LIKE #{name} </if>" +
            "<if test='semester != null'> AND semester = #{semester} </if>" +
            "<if test='ready != null'> AND ready = #{ready} </if>" +
            "</where>" +
            "</script>")
    List<Exam> getAllWithParams(@Param("teacherId") int teacherId,
                                @Param("name") String name,
                                @Param("semester") Integer semester,
                                @Param("ready") Boolean ready);

    @Select("SELECT exam.id AS id, exam.teacher_id, exam.name AS name, user.firstname AS firstName, user.lastname AS lastName, " +
            "user.patronymic AS patronymic, user_teacher.department AS department, user_teacher.position AS position, " +
            "exam.count AS questionsCountPerExam, exam.time AS timeInMinutes " +
            "FROM exam " +
            "JOIN user ON user.id = exam.teacher_id " +
            "JOIN user_teacher ON user_teacher.id = exam.teacher_id WHERE ready = true AND semester = #{semester}")
    List<ExamInfo> getAllExamInfo(int semester);

    @Select("SELECT DISTINCT(exam.id) AS id, exam.teacher_id, exam.name AS name, user.firstname AS firstName, user.lastname AS lastName, " +
            "user.patronymic AS patronymic, user_teacher.department AS department, user_teacher.position AS position, " +
            "exam.count AS questionsCountPerExam, exam.time AS timeInMinutes " +
            "FROM exam " +
            "JOIN user ON user.id = exam.teacher_id " +
            "JOIN user_teacher ON user_teacher.id = exam.teacher_id " +
            "JOIN student_exam ON exam.id IN (SELECT exam_id FROM student_exam WHERE student_id = #{studentId} AND CURRENT_TIMESTAMP(6) > enddate)")
    List<ExamInfo> getPassedExamInfo(int studentId);

    @Select("SELECT DISTINCT(exam.id) AS id, exam.teacher_id, exam.name AS name, user.firstname AS firstName, user.lastname AS lastName, " +
            "user.patronymic AS patronymic, user_teacher.department AS department, user_teacher.position AS position, " +
            "exam.count AS questionsCountPerExam, exam.time AS timeInMinutes " +
            "FROM exam " +
            "JOIN user ON user.id = exam.teacher_id " +
            "JOIN user_teacher ON user_teacher.id = exam.teacher_id " +
            "JOIN student_exam ON exam.id IN (SELECT exam_id FROM student_exam WHERE student_id = #{studentId} AND CURRENT_TIMESTAMP(6) < enddate)")
    List<ExamInfo> getCurrentExamInfo(int studentId);

    @Select("SELECT DISTINCT(exam.id) AS id, exam.teacher_id, exam.name AS name, user.firstname AS firstName, user.lastname AS lastName, " +
            "user.patronymic AS patronymic, user_teacher.department AS department, user_teacher.position AS position, " +
            "exam.count AS questionsCountPerExam, exam.time AS timeInMinutes " +
            "FROM exam " +
            "JOIN user ON user.id = exam.teacher_id " +
            "JOIN user_teacher ON user_teacher.id = exam.teacher_id " +
            "JOIN student_exam ON exam.id NOT IN (SELECT exam_id FROM student_exam WHERE student_id = #{studentId})")
    List<ExamInfo> getRemainingExamInfo(int studentId);

    @Select("SELECT exam.id AS id, exam.name AS name, exam.details AS showDetails, user.firstname AS firstName, user.lastname AS lastName, " +
            "user.patronymic AS patronymic, user_teacher.position AS position, user_teacher.department AS department, " +
            "exam.count AS questionsCountPerExam, student_exam.result AS tmpResult FROM student_exam " +
            "JOIN exam ON exam.id = student_exam.exam_id " +
            "JOIN user ON user.id = exam.teacher_id " +
            "JOIN user_teacher ON user_teacher.id = exam.teacher_id " +
            "WHERE student_exam.student_id = #{studentId}")
    List<ExamInfo> getStudentResults(int studentId);

    List<Group> getStudentsResult(int id);

    @Select("SELECT teacher_id FROM exam WHERE id = #{examId}")
    Integer getTeacherIdByExamId(int examId);

    @Update("UPDATE exam SET name = #{exam.examName}, semester = #{exam.semester}, ready = #{exam.ready}, time = #{exam.timeInMinutes}, " +
            "count = #{exam.questionsCountPerExam}, details = #{exam.showDetails} WHERE id = #{exam.id} AND teacher_id = #{teacherId}")
    void update(@Param("exam") Exam exam, @Param("teacherId") int teacherId);

    @Delete("DELETE FROM exam WHERE id = #{id}")
    void delete(Exam exam);

    @Select("SELECT EXISTS(SELECT 1 FROM exam WHERE name = #{name} AND semester = #{semester} LIMIT 1)")
    Boolean isExamWithSameNameAndSemesterExists(@Param("name") String name, @Param("semester") int semester);

    @Select("SELECT EXISTS(SELECT 1 FROM exam WHERE teacher_id = #{teacherId} AND id = #{examId} LIMIT 1)")
    Boolean anyAccess(@Param("teacherId") int teacherId, @Param("examId") int examId);

}
