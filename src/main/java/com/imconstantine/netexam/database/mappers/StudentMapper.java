package com.imconstantine.netexam.database.mappers;

import com.imconstantine.netexam.model.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface StudentMapper {

    @Insert("INSERT INTO user_student (id, semester, `group`) VALUES (#{id}, #{semester}, #{group})")
    Integer insert(Student student);

    @Update("UPDATE user_student SET semester = #{semester}, `group` = #{group} WHERE id = #{id}")
    void update(Student student);

    @Select("SELECT user.id, login, type, hashkey AS passwordHashKey, firstname, lastname, patronymic, `group`, semester FROM user, user_student " +
            "WHERE user.id IN (SELECT user_id FROM user_session WHERE token LIKE #{token}) " +
            "AND user_student.id IN (SELECT user_id FROM user_session WHERE token LIKE #{token})")
    Student getByToken(String token);

}
