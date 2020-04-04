package com.udacity.jwdnd.course1.cloudstorage.models;


import javax.persistence.*;

@Entity
@Table(name = "NOTES")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noteId;

    private String noteTitle;
    private String noteDescription;

    @ManyToOne
    @JoinColumn(name = "USERID", nullable = false)
    private User user;
}
