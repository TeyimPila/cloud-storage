package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByUsername(auth.getName());
    }

    public String getKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
