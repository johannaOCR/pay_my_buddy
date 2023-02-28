package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.repository.TransactionRepository;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@DynamicUpdate
@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    /**
     * Return all Transactions in DB
     * @return Iterable<Transaction>
     */
    public Iterable<Transaction> getTransactions(){
        return transactionRepository.findAll();
    }

    /**
     * Return an Optional Transaction by a given ID
     * @param id
     * @return Optional<Transaction>
     */
    public Optional<Transaction> getTransactionById(Integer id){
        return transactionRepository.findById(id);
    }

    /**
     * Save a Given transaction in DB
     * @param transaction
     * @return Transaction saved
     */
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    /**
     * Delete a given Transaction in DB
     * @param transaction
     * return void
     */
    public void deleteTransaction(Transaction transaction){
        transactionRepository.delete(transaction);
    }

    /**
     * Delete all Transaction in DB
     *
     */
    private void deleteAllTransaction(){
        transactionRepository.deleteAll();
    }

    /**
     * Delete Transaction by a ID given
     * @param id
     * return void
     */
    public void deleteTransactionById(Integer id){
        transactionRepository.deleteById(id);
    }

}
