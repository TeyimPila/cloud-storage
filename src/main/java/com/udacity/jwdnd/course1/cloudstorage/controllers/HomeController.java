package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    AuthService authService;
    @Autowired
    FileService fileService;
    @Autowired
    NoteService noteService;
    @Autowired
    CredentialService credentialService;
    @Autowired
    EncryptionService encryptionService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();

        User user = authService.currentUser();

        List<File> userFiles = fileService.getUserFiles(user);
        List<Note> userNotes = noteService.getUserNotes(user);
        List<Credential> userCredentials = credentialService.getUserCredentials(user);

        modelAndView.addObject("userName", "Welcome, " + user.getFirstName() + " " + user.getLastName());
        modelAndView.addObject("files", userFiles);
        modelAndView.addObject("notes", userNotes);
        modelAndView.addObject("credentials", userCredentials);
        modelAndView.addObject("encryptionService", encryptionService);
        modelAndView.setViewName("home");

        return modelAndView;
    }


}
