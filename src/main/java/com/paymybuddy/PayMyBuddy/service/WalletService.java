package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.repository.WalletRepository;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@DynamicUpdate
@Service
public class WalletService {

    @Autowired
    private UserService userService;

    /**
     * Supply the wallet balance
     * @param principal
     * @param amount
     * @return
     */
    public boolean supplyWalletBalance(Principal principal, float amount){
        boolean response = false;
        if(principal != null && amount>0){
            User user = userService.getUserByEmail(principal.getName());
            Wallet wallet = user.getWallet();
            wallet.setBalance(wallet.getBalance()+amount);
            userService.saveUser(user);
            response = true;
        }
        return response;
    }

}
