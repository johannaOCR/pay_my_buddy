package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.repository.WalletRepository;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@DynamicUpdate
@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    /**
     * Return all wallets in DB
     * @return Iterable<Wallet>
     */
    public Iterable<Wallet> getWallets(){
        return walletRepository.findAll();
    }

    /**
     * Return an Optional Wallet by a given ID
     * @param id
     * @return Optional<Wallet>
     */

    public Optional<Wallet> getWalletById(Integer id){
        return walletRepository.findById(id);
    }

    /**
     * Save a Given wallet in DB
     * @param wallet
     * @return Wallet saved
     */
    public Wallet saveWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    /**
     * Delete a given Wallet in DB
     * @param wallet
     * return void
     */
    public void deleteWallet(Wallet wallet){
        walletRepository.delete(wallet);
    }

    /**
     * Delete all Wallet in DB
     *
     */
    private void deleteAllWallets(){
        walletRepository.deleteAll();
    }

    /**
     * Delete Wallet by a ID given
     * @param id
     * return void
     */
    public void deleteWalletById(Integer id){
        walletRepository.deleteById(id);
    }


}
