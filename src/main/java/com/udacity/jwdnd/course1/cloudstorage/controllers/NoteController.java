package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private AuthService authService;

    @PostMapping("/saveNote")
    public ModelAndView createNote(@Valid Note note, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("result");
        modelAndView.addObject("note", note);

        try {
            User currentUser = authService.currentUser();

            if (!bindingResult.hasErrors()) {

                note.setUser(currentUser);
                note = noteService.createOrUpdate(note);

                if (note != null) {
                    modelAndView.addObject("alertClass", "alert-success");
                    modelAndView.addObject("message", "Note created successfully");
                }
            }
        } catch (Exception e) {
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "Something went wrong");
        }

        return modelAndView;
    }

    @PostMapping("/updateNote")
    public ModelAndView updateNote(@Valid Note note, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");

        try {

            if (!bindingResult.hasErrors()) {
                noteService.createOrUpdate(note);

                modelAndView.addObject("alertClass", "alert-success");
                modelAndView.addObject("message", "Note updated successfully");
            }
        } catch (Exception e) {
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "Something went wrong");
        }

        return modelAndView;
    }

    @PostMapping("/deleteNote/{noteId}")
    public ModelAndView deleteFile(@PathVariable Integer noteId) {
        // Load file from database

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");

        try {
            noteService.deleteNote(noteId);
            modelAndView.addObject("alertClass", "alert-success");
            modelAndView.addObject("message", "Note deleted successfully");
        } catch (Exception e) {
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "Something went wrong");
        }

        return modelAndView;
    }

}
