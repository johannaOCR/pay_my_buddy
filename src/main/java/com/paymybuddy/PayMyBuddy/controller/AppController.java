package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AppController {

    @Autowired
    private UserService userService;


    @GetMapping
    public String home(Principal principal, Model model) {
        User connectedUser = userService.getUserByEmail(principal.getName());
        model.addAttribute("firstName", connectedUser.getFirstname());
        model.addAttribute("balance", connectedUser.getWallet().getBalance());
        return "home";
    }
}
