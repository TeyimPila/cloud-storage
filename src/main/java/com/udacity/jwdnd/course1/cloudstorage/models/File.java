package com.udacity.jwdnd.course1.cloudstorage.models;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name = "FILES")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;

    private String fileName;
    private String contentType;
    private Long fileSize;

    @Lob
    private byte[] fileData;

    @ManyToOne
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    public File() {
    }

    public File(String fileName, String contentType, Long fileSize, byte[] fileData, User user) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileData = fileData;
        this.fileSize = fileSize;
        this.user = user;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
