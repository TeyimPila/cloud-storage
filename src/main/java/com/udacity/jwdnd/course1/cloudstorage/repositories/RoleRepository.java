package com.udacity.jwdnd.course1.cloudstorage.repositories;


import com.udacity.jwdnd.course1.cloudstorage.models.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RoleRepository {

    @Select("SELECT * FROM ROLES WHERE role = #{role}")
    Role findByRole(String role);
}