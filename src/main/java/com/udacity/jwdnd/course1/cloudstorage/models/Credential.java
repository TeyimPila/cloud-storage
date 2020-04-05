package com.udacity.jwdnd.course1.cloudstorage.models;


import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.hibernate.service.spi.InjectService;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "CREDENTIALS")
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer credentialId;

    @NotEmpty(message = "URL is required")
    @URL(message = "The Url you provided is not a valid url")
    private String url;

    @NotEmpty(message = "Username is required")
    private String username;

    private String key;

    @NotEmpty(message = "Password is required")
    private String password;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    public Credential() {
    }

    public Credential(String url, String username, String key, String password, User user) {
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.user = user;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "credentialId=" + credentialId +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", key='" + key + '\'' +
                ", password='" + password + '\'' +
                ", user=" + user +
                '}';
    }
}
