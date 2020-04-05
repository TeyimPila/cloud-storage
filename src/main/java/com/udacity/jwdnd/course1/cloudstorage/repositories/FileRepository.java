package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FileRepository {

    @Insert("INSERT INTO FILES(FILENAME, CONTENTTYPE, FILESIZE, FILEDATA, USERID) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{user.userId})")
    Integer save(File file);

    @Select("SELECT * FROM FILES WHERE FILEID = #{id}")
    File find(Integer id);

    @Select("SELECT * FROM FILES WHERE USERID = #{userId}")
    List<File> findByUser(User user);
}
