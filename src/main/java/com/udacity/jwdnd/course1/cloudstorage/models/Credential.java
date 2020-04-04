package com.udacity.jwdnd.course1.cloudstorage.models;


import javax.persistence.*;

@Entity
@Table(name = "CREDENTIALS")
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer credentialId;

    private String url;
    private String username;
    private String key;
    private String password;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user;

}
