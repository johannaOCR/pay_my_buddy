package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.dto.TransactionDTO;
import com.paymybuddy.PayMyBuddy.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;



@SpringBootTest
public class TransactionServiceTest {



    @TestConfiguration
    static class TransactionServiceTestContextConfiguration {
        @Bean
        public TransactionService transactionServiceTest() {
            return new TransactionService();
        }
    }
    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    public void getTransactionsByUserIdTest(){
        List<TransactionDTO> transactions = new ArrayList<>(transactionService.getTransactionsByUser(new Principal() {
            @Override
            public String getName() {
                return"johanna.tristan@gmail.com";
            }
        }));
        transactions.forEach(transactionDTO -> System.out.println(
                transactionDTO.getNameCreditor() + " | " + transactionDTO.getNameDebtor() + " | " +  transactionDTO.getDescription() + " | " +  transactionDTO.getAmount() +"\r"));

    }


}
