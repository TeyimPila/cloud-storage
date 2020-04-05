package com.udacity.jwdnd.course1.cloudstorage.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "NOTES")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noteId;

    @NotEmpty(message = "Please provide a title for the note")
    private String noteTitle;

    @NotEmpty(message = "Please provide a description for the note")
    private String noteDescription;

    @ManyToOne
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    public Note() {
    }

    public Note(String noteTitle, String noteDescription, User user) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.user = user;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteDescription='" + noteDescription + '\'' +
                ", user=" + user +
                '}';
    }
}
