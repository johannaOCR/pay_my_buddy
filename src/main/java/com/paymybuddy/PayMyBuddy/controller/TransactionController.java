package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.dto.ContactDTO;
import com.paymybuddy.PayMyBuddy.dto.TransactionDTO;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.service.TransactionService;
import com.paymybuddy.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/transfer")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;


    @GetMapping
    public String getTransfer(Principal principal, Model model){
        List<TransactionDTO> transactions = transactionService.getTransactionsByUser(principal);
        List<ContactDTO> contacts = userService.getContactsByUser(principal);
        model.addAttribute("transactions", transactions);
        model.addAttribute("contacts", contacts);
        return "transfer";
    }
    /*
    @PostMapping("/addTransfer")
    public String addTransfer(Principal principal, @RequestParam(value="amount")BigDecimal amount,
                              @RequestParam(value="description") String description,
                              @RequestParam(value="contactId") Long contactId) throws ChangeSetPersister.NotFoundException, ClassNotFoundException {
        moneyTransferService.addMoneyTransfer(principal, contactId, amount, description);
        return "redirect:/transfer";
    }
                              */

    @GetMapping("/transfer")
    public String transfert(){
        return "transfer";
    }
}
