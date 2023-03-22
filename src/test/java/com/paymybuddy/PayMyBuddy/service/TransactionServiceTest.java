package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.dto.TransactionDTO;
import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.repository.TransactionRepository;
import com.paymybuddy.PayMyBuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {


    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private Principal principal;

    @Mock
    private UserService userService;

    @Test
    void testAddBankTransaction(){
        User user = new User();
        user.setFirstname("testFN");
        user.setLastname("testLN");
        user.setPassword("testPW");
        user.setEmail("testEM@mail.com");
        user.setUserId(1);

        Wallet wallet = new Wallet();
        wallet.setBalance(50);
        wallet.setWalletId(1);
        wallet.setUser(user);
        user.setWallet(wallet);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountId(1);
        bankAccount.setBic("test");
        bankAccount.setIban("test");
        bankAccount.setWallet(wallet);
        wallet.setBankAccounts(bankAccount);

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(principal.getName()).thenReturn(user.getEmail());

        boolean response = transactionService.addTransactionToBank(principal,12);

        assertThat(response).isTrue();
    }

    @Test
    void testAddTransaction(){
        User user = new User();
        user.setFirstname("testFN");
        user.setLastname("testLN");
        user.setPassword("testPW");
        user.setEmail("testEM@mail.com");
        user.setUserId(1);

        User contact = new User();
        contact.setFirstname("contacttestFN");
        contact.setLastname("contacttestLN");
        contact.setPassword("contacttestPW");
        contact.setEmail("contacttestEM@mail.com");
        contact.setUserId(2);

        Wallet wallet = new Wallet();
        wallet.setBalance(50);
        wallet.setWalletId(1);
        wallet.setUser(user);
        user.setWallet(wallet);

        Wallet wallet2 = new Wallet();
        wallet2.setBalance(50);
        wallet2.setWalletId(2);
        wallet2.setUser(contact);
        contact.setWallet(wallet2);

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        when(userService.getUserByEmail(contact.getEmail())).thenReturn(contact);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(userRepository.findByEmail(contact.getEmail())).thenReturn(contact);
        when(principal.getName()).thenReturn(user.getEmail());

        boolean response = transactionService.addTransaction(principal,"test",12,contact.getEmail());

        assertThat(response).isTrue();
    }

    @Test
    void testGetTransactionsByUser(){
        User user = new User();
        user.setFirstname("testFN");
        user.setLastname("testLN");
        user.setPassword("testPW");
        user.setEmail("testEM@mail.com");
        user.setUserId(1);

        Wallet wallet = new Wallet();
        wallet.setBalance(50);
        wallet.setWalletId(1);
        wallet.setUser(user);
        user.setWallet(wallet);

        Transaction transaction = new Transaction();
        transaction.setDescription("test");
        transaction.setBankTransaction(true);
        transaction.setDate(new Date());
        transaction.setWalletCreditor(wallet);
        transaction.setWalletDebtor(wallet);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        when(transactionRepository.findAllByWalletCreditorId(anyInt())).thenReturn(transactions);
        when(transactionRepository.findAllByWalletDebtorId(anyInt())).thenReturn(transactions);
        when(principal.getName()).thenReturn(user.getEmail());
        when(userService.getUserById(anyInt())).thenReturn(java.util.Optional.of(user));
        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);

        transactionService.getTransactionsByUser(principal);

        verify(transactionRepository,times(1)).findAllByWalletCreditorId(anyInt());
        verify(transactionRepository,times(1)).findAllByWalletDebtorId(anyInt());

    }


}
