package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private static final Logger logger = LogManager.getLogger("UserController");
    @Autowired
    private UserService userService;

    @GetMapping("/sign-up")
    public String signUp(@ModelAttribute User user) {
        return "signup";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user) {
        logger.info("Try to create new user" + user.toString());
        boolean isUserCreated = userService.addUser(user);
        if(isUserCreated){

            return "redirect:/login";
        } else {
            return "redirect:/sign-up";
        }

    }
}
