package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public String createNote(@Valid Note note, BindingResult bindingResult) {

        System.out.println("The note" + note.toString());
        ModelAndView modelAndView = new ModelAndView();

        try {
            User currentUser = authService.currentUser();

            if (!bindingResult.hasErrors()) {

                note.setUser(currentUser);
                note = noteService.createOrUpdate(note);

                if (note != null) {
                    modelAndView.addObject("alertClass", "alert-success");
                    modelAndView.addObject("message", "User has been registered successfully");
                }
            }
        } catch (Exception e) {
//            modelAndView.addObject("alertClass", "alert-danger");
//            modelAndView.addObject("message", "Something went wrong");
        }

        return "redirect:/home";
    }

    @PostMapping("/updateNote")
    public String updateNote(@Valid Note note, BindingResult bindingResult) {

        System.out.println("The note update " + note.toString());
        ModelAndView modelAndView = new ModelAndView();

        try {

            if (!bindingResult.hasErrors()) {

                note = noteService.createOrUpdate(note);

                if (note != null) {
                    modelAndView.addObject("alertClass", "alert-success");
                    modelAndView.addObject("message", "User has been registered successfully");
                }
            }
        } catch (Exception e) {
//            modelAndView.addObject("alertClass", "alert-danger");
//            modelAndView.addObject("message", "Something went wrong");
        }

        return "redirect:/home";
    }

    @PostMapping("/deleteNote/{noteId}")
    public String deleteFile(@PathVariable Integer noteId) {
        // Load file from database
        noteService.deleteNote(noteId);

        return "redirect:/home";
    }

}
