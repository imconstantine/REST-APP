package com.imconstantine.netexam.database.mappers;

import com.imconstantine.netexam.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SessionMapper {

    @Insert("INSERT INTO user_session (token, user_id) VALUES (#{token}, #{user.id})")
    void insert(@Param("token") String token, @Param("user") User user);

    @Select("SELECT user_id AS id FROM user_session WHERE token LIKE #{token}")
    Integer getIdByToken(String token);

    @Delete("DELETE FROM user_session WHERE token LIKE #{token}")
    void delete(String token);

    @Select("SELECT EXISTS(SELECT 1 FROM user_session WHERE token = #{token} LIMIT 1)")
    Boolean isTokenExist(String token);
}
