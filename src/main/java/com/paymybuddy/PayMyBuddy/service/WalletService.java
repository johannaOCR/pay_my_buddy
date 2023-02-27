package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    public Iterable<Wallet> getWallets(){
        return walletRepository.findAll();
    }

    public Optional<Wallet> getWalletById(Integer id){
        return walletRepository.findById(id);
    }

    public Wallet saveWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

}
