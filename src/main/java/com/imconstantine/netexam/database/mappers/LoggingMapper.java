package com.imconstantine.netexam.database.mappers;

import com.imconstantine.netexam.model.User;
import org.apache.ibatis.annotations.Select;

public interface LoggingMapper {

    @Select("SELECT id, login, type, hashkey AS passwordHashKey FROM user WHERE login = #{login}")
    User getUserByLogin(String login);

}
