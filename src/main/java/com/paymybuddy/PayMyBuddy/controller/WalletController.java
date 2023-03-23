package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.service.UserService;
import com.paymybuddy.PayMyBuddy.service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class WalletController {
    private static final Logger logger = LogManager.getLogger("UserController");

    @Autowired
    private WalletService   walletService;

    @PostMapping("/supplyBalance")
    public String supplyBalance(
            @RequestParam(name="cardName") String cardName,
            @RequestParam (name="cardNumber") String cardNumber,
            @RequestParam (name="expirationDate") String expirationDate,
            @RequestParam (name="CVV") String CVV,
            @RequestParam (name="amount") float amount,
            Principal principal ){
    walletService.supplyWalletBalance(principal,amount);
        return "redirect:/profile";
    }
}
