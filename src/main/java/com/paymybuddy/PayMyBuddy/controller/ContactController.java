package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.dto.ContactDTO;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ContactController {
    private static final Logger logger = LogManager.getLogger("UserController");

    @Autowired
    private UserService userService;

    @GetMapping("/contact")
    public String contact(Principal principal, Model model){
        List<ContactDTO> contacts = userService.getContactsByUser(principal);
        model.addAttribute("contacts", contacts);
        return "contact";
    }

    @PostMapping("/addContact")
    public String addContact(@RequestParam(name="email") String email, Principal principal ){
        User user = userService.getUserByEmail(principal.getName());
        User contact = userService.getUserByEmail(email);
        user.addContact(contact);
        userService.saveUser(user);
        return "redirect:/contact";
    }

    @PostMapping("/deleteContact")
    public String deleteBankAccount(@RequestParam (name="email") String email, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        User contact = userService.getUserByEmail(email);
        user.getContacts().remove(contact);
        userService.saveUser(user);
        return "redirect:/contact";
    }
}
