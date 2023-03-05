package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.dto.TransactionDTO;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTest {

    private static final Logger logger = LogManager.getLogger("TransactionServiceTest");

    @TestConfiguration
    static class TransactionServiceTestContextConfiguration {
        @Bean
        public TransactionService transactionServiceTest() {
            return new TransactionService();
        }
    }
/*
    @Before
    public void setUp(){

        User userCreditor = new User(1,"johanna","tristan","johanna.t@mail.com","passmdp",null,null);
        Wallet walletCreditor = new Wallet(1,userCreditor,null,0,null,null);
        userCreditor.setWallet(walletCreditor);

        User userDebtor = new User(2,"thomas","pere","t.p@mail.com","passmdp",null,null);
        Wallet walletDebtor = new Wallet(2,userDebtor,null,0,null,null);
        userDebtor.setWallet(walletDebtor);


        List<Transaction> transactions= new ArrayList<>();
        transactions.add(new Transaction());
        when(transactionRepository.findAllByWalletCreditorId(1)).thenReturn(transactions);
    }
    */
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
