package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NoteRepository {

    @Insert("INSERT INTO NOTES(NOTETITLE, NOTEDESCRIPTION, USERID) VALUES (#{noteTitle}, #{noteDescription}, #{user.userId})")
    Integer save(Note file);

    @Insert("UPDATE NOTES SET NOTETITLE=#{noteTitle}, NOTEDESCRIPTION=#{noteDescription} WHERE NOTEID=#{noteId}")
    Integer update(Note file);

    @Select("SELECT * FROM NOTES WHERE NOTEID = #{id}")
    Note find(Integer id);

    @Select("SELECT * FROM NOTES WHERE USERID = #{userId}")
    List<Note> findByUser(User user);

    @Delete("DELETE FROM NOTES WHERE NOTEID = #{noteId}")
    void deleteNote(Integer noteId);
}
