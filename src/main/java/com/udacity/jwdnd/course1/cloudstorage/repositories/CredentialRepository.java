package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CredentialRepository {

    @Insert("INSERT INTO CREDENTIALS(URL, USERNAME, `KEY`, PASSWORD, USERID) VALUES (#{url}, #{username}, #{key}, #{password}, #{user.userId})")
    Integer save(Credential file);

    @Select("SELECT * FROM CREDENTIALS WHERE CREDENTIALID = #{id}")
    Credential find(Integer id);

    @Select("SELECT * FROM CREDENTIALS WHERE USERID = #{userId}")
    List<Credential> findByUser(User user);

    @Insert("UPDATE CREDENTIALS SET URL=#{url}, USERNAME=#{username}, `KEY`=#{key}, PASSWORD=#{password} WHERE CREDENTIALID=#{credentialId}")
    Integer update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE CREDENTIALID = #{credId}")
    void deleteCredential(Integer credId);

    @Delete("DELETE FROM CREDENTIALS")
    void deleteAll();
}
