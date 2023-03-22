package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.BankAccountRepository;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@DynamicUpdate
@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserService userService;

    public Boolean deleteBankAccount(Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        boolean response = false;
        if (user != null) {
            int idBankAccountToDelete = user.getWallet().getBankAccounts().getBankAccountId();
            user.getWallet().setBankAccounts(null);
            userService.saveUser(user);
            this.deleteBankAccountById(idBankAccountToDelete);
            response = true;
        }
        return response;
    }

    public Boolean addBankAccount(String bic, String iban, Principal principal){
        boolean response = false;
        User user = userService.getUserByEmail(principal.getName());
        if(user!= null) {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setIban(iban);
            bankAccount.setBic(bic);
            user.getWallet().setBankAccounts(bankAccount);
            bankAccount.setWallet(user.getWallet());
            this.saveBankAccount(bankAccount);
            userService.saveUser(user);
            response = true;
        }
        return response;
    }
    /**
     * Save a Given BankAccount in DB
     * @param bankAccount
     * @return BankAccount saved
     */
    public BankAccount saveBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    /**
     * Delete BankAccount by a ID given
     * @param id
     * return void
     */
    public boolean deleteBankAccountById(Integer id) {
        bankAccountRepository.deleteById(id);
        if(bankAccountRepository.findById(id).isPresent()) {
            return true;
        } else{
            return false;
        }
    }


}
