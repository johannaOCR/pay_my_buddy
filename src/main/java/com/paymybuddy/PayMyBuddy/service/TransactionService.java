package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.dto.TransactionDTO;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@DynamicUpdate
@Service
public class TransactionService {
    private static final Logger logger = LogManager.getLogger("TransactionService");
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

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
    public void deleteTransactionById(int id){
        transactionRepository.deleteById(id);
    }

    @Transactional
    public List<TransactionDTO> getTransactionsByUser(Principal principal) {
        List<Transaction> transactions = new ArrayList<>();
        List<TransactionDTO> transactionDTO = new ArrayList<>();
        int id = userService.getUserByEmail(principal.getName()).getUserId();
        try{
            if(userService.getUserById(id).isPresent()) {
                User user = userService.getUserById(id).get();
                Wallet wallet = user.getWallet();
               transactions =  transactionRepository.findAllByWalletCreditorId(wallet.getWalletId());
               transactions.addAll(transactionRepository.findAllByWalletDebtorId(wallet.getWalletId()));
               transactions.forEach(transaction -> {
                           transactionDTO.add(
                                   new  TransactionDTO(
                                        transaction.getDescription(),
                                           transaction.getWalletDebtor().getUser().getFirstname() + " " + transaction.getWalletDebtor().getUser().getLastname(),
                                           transaction.getWalletCreditor().getUser().getFirstname() + " " + transaction.getWalletCreditor().getUser().getLastname(),
                                        transaction.getAmount())
                           );
               }
               );
            }
        } catch (Exception e ){
            logger.error(e.getMessage());
        }
        return transactionDTO;

    }

}
