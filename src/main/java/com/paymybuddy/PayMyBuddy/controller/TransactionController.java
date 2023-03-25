package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.dto.ContactDTO;
import com.paymybuddy.PayMyBuddy.dto.TransactionDTO;
import com.paymybuddy.PayMyBuddy.service.TransactionService;
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
public class TransactionController {
    private static final Logger logger = LogManager.getLogger("TransactionController");
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/transfer")
    public String getTransfer(Principal principal, Model model) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByUser(principal);
        List<ContactDTO> contacts = userService.getContactsByUser(principal);
        model.addAttribute("transactions", transactions);
        model.addAttribute("contacts", contacts);
        return "transfer";
    }

    @PostMapping("/addTransfer")
    public String addTransfer(Principal principal,
                              @RequestParam(value = "amount") float amount,
                              @RequestParam(value = "description") String description,
                              @RequestParam(value = "emailContact") String emailContact
    ) {
        transactionService.addTransaction(principal, description, amount, emailContact);
        return "redirect:/transfer";
    }

    @PostMapping("/transferToBank")
    public String transferToBank(
            @RequestParam(name = "amount") float amount,
            Principal principal) {
        transactionService.addTransactionToBank(principal, amount);
        return "redirect:/profile";
    }
}
