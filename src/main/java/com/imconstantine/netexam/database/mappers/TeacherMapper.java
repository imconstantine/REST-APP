package com.imconstantine.netexam.database.mappers;

import com.imconstantine.netexam.model.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TeacherMapper {

    @Insert("INSERT INTO user_teacher (id, department, position) VALUES (#{id}, #{department}, #{position})")
    void insert(Teacher teacher);

    @Update("UPDATE user_teacher SET department = #{department}, position = #{position} WHERE id = #{id}")
    void update(Teacher teacher);

    @Select("SELECT user.id, login, type, hashkey AS passwordHashKey, firstname, lastname, patronymic, department, position FROM user, user_teacher " +
            "WHERE user.id IN (SELECT user_id FROM user_session WHERE token LIKE #{token}) " +
            "AND user_teacher.id IN (SELECT user_id FROM user_session WHERE token LIKE #{token})")
    Teacher getByToken(String token);

}
