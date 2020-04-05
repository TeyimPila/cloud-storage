package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.crypto.Cipher;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private EncryptionService encryptionService;


    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView registration() {

        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            User userExists = userService.findUserByUsername(user.getUsername());
            if (userExists != null) {
                bindingResult.rejectValue("username", "error.user",
                        "There is already a user registered with the user name provided");
            }

            if (!bindingResult.hasErrors()) {
                User savedUser = userService.saveUser(user);

                if (savedUser != null) {
                    modelAndView.addObject("alertClass", "alert-success");
                    modelAndView.addObject("message", "User has been registered successfully");
                    modelAndView.addObject("user", new User());
                }

            }
        } catch (Exception e) {
            System.out.println("Here now 2 " + e.getMessage());
            modelAndView.addObject("alertClass", "alert-danger");
            modelAndView.addObject("message", "Something went wrong");
        }

        modelAndView.setViewName("signup");
        return modelAndView;
    }

}
