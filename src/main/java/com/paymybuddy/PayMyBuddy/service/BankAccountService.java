package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.repository.BankAccountRepository;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@DynamicUpdate
@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    /**
     * Return all BankAccount in DB
     * @return Iterable<BankAccount>
     */
    public Iterable<BankAccount> getBankAccounts(){
        return bankAccountRepository.findAll();
    }

    /**
     * Return an Optional BankAccount by a given ID
     * @param id
     * @return Optional<BankAccount>
     */
    public Optional<BankAccount> getBankAccountById(Integer id){
        return bankAccountRepository.findById(id);
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
     * Delete a given BankAccount in DB
     * @param bankAccount
     * return void
     */
    public void deleteBankAccount(BankAccount bankAccount){
        bankAccountRepository.delete(bankAccount);
    }

    /**
     * Delete all BankAccount in DB
     *
     */
    private void deleteAllBankAccount(){
        bankAccountRepository.deleteAll();
    }

    /**
     * Delete BankAccount by a ID given
     * @param id
     * return void
     */
    public void deleteBankAccountById(Integer id){
        bankAccountRepository.deleteById(id);
    }


}
