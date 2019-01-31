package com.imconstantine.netexam.database.mappers;

import com.imconstantine.netexam.model.Student;
import com.imconstantine.netexam.model.User;
import com.imconstantine.netexam.model.Teacher;
import com.imconstantine.netexam.model.UserType;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Insert("INSERT INTO user (login, type, hashkey, firstname, lastname, patronymic) " +
            "VALUES (#{login}, #{type}, #{passwordHashKey}, #{firstName}, #{lastName}, #{patronymic})")
    @Options(useGeneratedKeys = true)
    Integer insert(User user);

    @Select("SELECT user.id, login, type, hashKey, firstname, lastname, patronymic, user_teacher.department, user_teacher.position " +
            "FROM user, user_teacher WHERE user.id = #{id} AND user_teacher.id = #{id}")
    Teacher getTeacherById(int id);

    @Select("SELECT user.id, login, type, hashKey, firstname, lastname, patronymic, user_student.semester, user_student.`group`" +
            "FROM user, user_student WHERE user.id = #{id} AND user_student.id = #{id}")
    Student getStudentById(int id);

    @Update("UPDATE user SET firstname = #{firstName}, lastName = #{lastName}, patronymic = #{patronymic}, hashKey = #{hashKey} WHERE id = #{id}")
    void update(User user);

    @Select("SELECT type FROM user WHERE id IN (SELECT user_id FROM user_session WHERE token LIKE #{token})")
    UserType getUserTypeByToken(String token);

    @Select("SELECT * FROM user WHERE id IN (SELECT user_id FROM user_session WHERE token LIKE #{token})")
    User getUserByToken(String token);

    @Select("SELECT type FROM user WHERE id = #{id}")
    UserType getUserType(int id);

    @Select("SELECT EXISTS(SELECT 1 FROM user WHERE login = #{login} LIMIT 1)")
    Boolean isExist(String login);

    @Delete("DELETE FROM user")
    void deleteAll();

}
