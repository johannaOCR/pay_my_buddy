package com.paymybuddy.PayMyBuddy.model;

import com.paymybuddy.PayMyBuddy.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TransactionTest {
@Autowired
private TransactionService transactionService;
/*
    @Test
    public void getTransactionsByUserIdTest(){
        int id = 1;
        List<Transaction> transactions = transactionService.getTransactionsByUserId(id);

    }
*/
}
