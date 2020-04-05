package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private AuthService authService;

    @PostMapping("/saveCredential")
    public ModelAndView createCredential(@Valid Credential credential, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("result");
        modelAndView.addObject("credential", credential);

        try {

            credential.setKey(authService.getKey());

            if (!bindingResult.hasErrors()) {
                User currentUser = authService.currentUser();

                credential.setUser(currentUser);
                credential = credentialService.createOrUpdate(credential);

                modelAndView.addObject("alertClass", "alert-success");
                modelAndView.addObject("message", "Credential saved successfully");

            }
        } catch (Exception e) {
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "Something went wrong");
        }

        return modelAndView;
    }

    @PostMapping("/updateCredential")
    public ModelAndView updateCredential(@Valid Credential credential, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("result");
        modelAndView.addObject("credential", credential);

        try {

            credential.setKey(authService.getKey());

            if (!bindingResult.hasErrors()) {

                credential = credentialService.createOrUpdate(credential);

                modelAndView.addObject("alertClass", "alert-success");
                modelAndView.addObject("message", "Credentials updated successfully");

            }
        } catch (Exception e) {
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "Something went wrong");
        }

        return modelAndView;
    }

    @PostMapping("/deleteCredential/{credentialId}")
    public ModelAndView deleteFile(@PathVariable Integer credentialId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");

        try {
            credentialService.deleteCredential(credentialId);
            modelAndView.addObject("alertClass", "alert-success");
            modelAndView.addObject("message", "Credentials deleted successfully");
        } catch (Exception e) {
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "Something went wrong");
        }

        return modelAndView;
    }
}
