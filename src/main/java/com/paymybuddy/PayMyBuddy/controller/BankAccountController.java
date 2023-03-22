package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.service.BankAccountService;
import com.paymybuddy.PayMyBuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class BankAccountController {
    private static final Logger logger = LogManager.getLogger("BankAccountController");

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/addBankAccount")
    public String saveBankAccount(@RequestParam(name="bic") String bic,
                                  @RequestParam (name="iban") String iban,
                                  Principal principal )
    {
        logger.info("Trying to add a bankAccount");
        bankAccountService.addBankAccount(bic, iban, principal);
        return "redirect:/profile";
    }

    @PostMapping("/deleteBankAccount")
    public String deleteBankAccount(Principal principal){
        bankAccountService.deleteBankAccount(principal);
        return "redirect:/profile";
    }
}
