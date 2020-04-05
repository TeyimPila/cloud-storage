package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private AuthService authService;

    @PostMapping("/saveCredential")
    public String createCredential(@Valid Credential credential, BindingResult bindingResult) {

        System.out.println("The credential" + credential.toString());

        ModelAndView modelAndView = new ModelAndView();

        try {

            credential.setKey(authService.getKey());

            if (!bindingResult.hasErrors()) {
                System.out.println("The credential z" + credential.toString());
                User currentUser = authService.currentUser();

                credential.setUser(currentUser);
                credential = credentialService.createOrUpdate(credential);

                if (credential != null) {
                    modelAndView.addObject("alertClass", "alert-success");
                    modelAndView.addObject("message", "User has been registered successfully");
                }
            }
        } catch (Exception e) {
            throw e;
//            modelAndView.addObject("alertClass", "alert-danger");
//            modelAndView.addObject("message", "Something went wrong");
        }

        return "redirect:/home";
    }
}
