package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.dto.TransactionDTO;
import com.paymybuddy.PayMyBuddy.model.BankAccount;
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
import java.util.Date;
import java.util.List;

@DynamicUpdate
@Service
public class TransactionService {

    private static final Logger logger = LogManager.getLogger("TransactionService");

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;


    /**
     * Save a Given transaction in DB
     *
     * @param transaction
     * @return Transaction saved
     */
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Transactional
    public List<TransactionDTO> getTransactionsByUser(Principal principal) {
        List<Transaction> transactions = new ArrayList<>();
        List<TransactionDTO> transactionDTO = new ArrayList<>();
        int id = userService.getUserByEmail(principal.getName()).getUserId();
        try {
            if (userService.getUserById(id).isPresent()) {
                User user = userService.getUserById(id).get();
                Wallet wallet = user.getWallet();
                transactions = transactionRepository.findAllByWalletCreditorId(wallet.getWalletId());
                List<Transaction> transactionsDebtor = new ArrayList<>();
                transactionsDebtor = transactionRepository.findAllByWalletDebtorId(wallet.getWalletId());
                List<Transaction> transactionsFinal = new ArrayList<>();

                for (Transaction transaction : transactionsDebtor) {
                    if (!transactions.contains(transaction)) {
                        transactionsFinal.add(transaction);
                    }
                }
                transactions.addAll(transactionsFinal);
                transactions.forEach(transaction -> {
                            transactionDTO.add(
                                    new TransactionDTO(
                                            transaction.getDescription(),
                                            transaction.getWalletDebtor().getUser().getFirstname() + " " + transaction.getWalletDebtor().getUser().getLastname(),
                                            transaction.getWalletCreditor().getUser().getFirstname() + " " + transaction.getWalletCreditor().getUser().getLastname(),
                                            transaction.getAmount(),
                                            transaction.getDateFormated()
                                    )
                            );
                        }
                );
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return transactionDTO;

    }

    public boolean addTransaction(Principal principal, String description, float amount, String emailContact) {
        boolean response = false;
        User user = userService.getUserByEmail(principal.getName());
        if (user.getWallet().getBalance() >= amount) {
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
            this.saveTransaction(transaction);
            response = true;
        } else {
            logger.error("Le montant renseigné n'est pas le bon");
        }
        return response;
    }

    public boolean addTransactionToBank(Principal principal, float amount) {
        boolean response = false;
        if (principal != null) {
            User user = userService.getUserByEmail(principal.getName());
            Wallet wallet = user.getWallet();
            if (wallet.getBalance() >= amount) {
                BankAccount bankAccount = wallet.getBankAccounts();

                // création de la transaction
                Transaction transaction = new Transaction();
                transaction.setBankTransaction(true);
                transaction.setAmount(amount);
                transaction.setWalletDebtor(wallet);
                transaction.setWalletCreditor(wallet);
                transaction.setDescription("Bank transaction");
                transaction.setDate(new Date());

                // soustraction du montant sur la balance
                wallet.setBalance(wallet.getBalance() - amount);
                logger.info(wallet.getBalance());
                userService.saveUser(user);
                this.saveTransaction(transaction);
                response = true;
            } else {
                logger.error("amount bigger than user's balance");
            }
        }
        return response;
    }
}
