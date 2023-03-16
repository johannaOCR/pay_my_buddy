package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.dto.ContactDTO;
import com.paymybuddy.PayMyBuddy.dto.TransactionDTO;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.service.TransactionService;
import com.paymybuddy.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;


    @GetMapping("/transfer")
    public String getTransfer(Principal principal, Model model){
        List<TransactionDTO> transactions = transactionService.getTransactionsByUser(principal);
        List<ContactDTO> contacts = userService.getContactsByUser(principal);
        model.addAttribute("transactions", transactions);
        model.addAttribute("contacts", contacts);
        return "transfer";
    }

    @PostMapping("/addTransfer")
    public String addTransfer(Principal principal,
                              @RequestParam(value="amount")float amount,
                              @RequestParam(value="description") String description,
                              @RequestParam(value="emailContact") String emailContact,
                              Model model)
    {
        User user = userService.getUserByEmail(principal.getName());
        if(user.getWallet().getBalance()>=amount){
            Wallet walletUser = user.getWallet();
            User contact = userService.getUserByEmail(emailContact);
            Wallet walletContact = contact.getWallet();
            contact.getWallet().setBalance(walletContact.getBalance() + amount);
            user.getWallet().setBalance(walletUser.getBalance() - amount);
            Transaction transaction = new Transaction();
            transaction.setDate(new Date());
            transaction.setDescription(description);
            transaction.setWalletCreditor(walletContact);
            transaction.setWalletDebtor(walletUser);
            transaction.setAmount(amount);
            userService.saveUser(user);
            userService.saveUser(contact);
            transactionService.saveTransaction(transaction);
        } else {
            String message = "Le montant renseigné n'est pas le bon";
            model.addAttribute("badAmount", message);
        }
        return "redirect:/transfer";
    }



}
