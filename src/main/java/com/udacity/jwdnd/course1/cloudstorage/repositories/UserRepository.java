package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserRepository {

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User findByUsername(String username);

    @Insert("INSERT INTO USERS(username, salt, password, FIRSTNAME, LASTNAME) VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    Integer save(User user);

    @Select("SELECT * FROM USERS WHERE USERID = #{id}")
    User find(Integer id);
}
