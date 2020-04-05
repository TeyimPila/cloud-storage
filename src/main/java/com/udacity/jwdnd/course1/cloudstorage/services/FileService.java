package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File storeFile(MultipartFile file, User user) {

        String originalFileName = file.getOriginalFilename();


        if (originalFileName == null) {
//                            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        // Normalize file name
        String fileName = StringUtils.cleanPath(originalFileName);

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            File File = new File(fileName, file.getContentType(), file.getSize(), file.getBytes(), user);

            Integer fileId = fileRepository.save(File);


            return fileRepository.find(fileId);
        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
        return null;

    }

    public File getFile(Integer fileId) {
        File file = fileRepository.find(fileId);

        if (file == null) {
//            throw new MyFileNotFoundException("File not found with id " + fileId)
        }

        return file;
    }

    public List<File> getUserFiles(User user) {
        return fileRepository.findByUser(user);
    }

    public void deleteFile(Integer fileId) {
        fileRepository.deleteFile(fileId);
    }

}
