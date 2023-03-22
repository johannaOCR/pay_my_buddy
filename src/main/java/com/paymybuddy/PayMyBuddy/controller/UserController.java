package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @GetMapping("/profile")
    public String profil(Principal principal, Model model){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user",user);
        model.addAttribute("bankAccount",user.getWallet().getBankAccounts());
        model.addAttribute("wallet",user.getWallet());
        return "profile";
    }

    @Transactional
    @PostMapping("/updateProfile")
    public String updateProfile(
            @RequestParam (name="lastname") String lastname,
            @RequestParam (name="firstname") String firstname,
            @RequestParam (name="bic") String bic,
            @RequestParam (name="iban") String iban,
            Principal principal )
    {
        logger.info("Try to update profile");
       updateProfile(lastname,firstname,bic,iban,principal);
       return "redirect:/profile";
    }

    @PostMapping("deleteUser")
    public String deleteUser(Principal principal){
        logger.info("Try to delete user");
        userService.deleteUser(userService.getUserByEmail(principal.getName()));
        return "redirect:/login";
    }
}
