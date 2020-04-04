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
    private String fileSize;
    @Lob
    private Blob fileData;

    @ManyToOne
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    public File() {
    }
}
