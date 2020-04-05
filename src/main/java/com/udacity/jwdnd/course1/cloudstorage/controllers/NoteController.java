package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

//    @PostMapping("/uploadMultipleFiles")
//    public ModelAndView uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//
//        return Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file))
//                .collect(Collectors.toList());
//    }

//    @GetMapping("/downloadFile/{fileId}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId) {
//        // Load file from database
//        File file = fileService.getFile(fileId);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(file.getContentType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
//                .body(new ByteArrayResource(file.getFileData()));
//    }

}
