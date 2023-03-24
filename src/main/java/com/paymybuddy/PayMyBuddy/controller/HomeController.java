package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import java.text.DecimalFormat;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String home(Principal principal, Model model) {
        User connectedUser = userService.getUserByEmail(principal.getName());
        model.addAttribute("firstname", connectedUser.getFirstname());
        DecimalFormat df = new DecimalFormat("0.00");
        model.addAttribute("balance", df.format(connectedUser.getWallet().getBalance()));
        return "home";
    }
}
