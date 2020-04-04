package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.models.Role;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserRoleRepository {

    @Insert("INSERT INTO USER_ROLES(USERID, ROLEID) VALUES (#{user.userId}, #{role.roleId})")
    void save(User user, Role role);
}
