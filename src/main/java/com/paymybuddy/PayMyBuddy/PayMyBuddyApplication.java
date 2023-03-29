package com.paymybuddy.PayMyBuddy;

import com.paymybuddy.PayMyBuddy.service.BankAccountService;
import com.paymybuddy.PayMyBuddy.service.TransactionService;
import com.paymybuddy.PayMyBuddy.service.UserService;
import com.paymybuddy.PayMyBuddy.service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;


@SpringBootApplication
@ComponentScan(basePackages = "com.paymybuddy.PayMyBuddy.*")
@EntityScan("com.paymybuddy.PayMyBuddy.*")
@EnableJpaRepositories(basePackages = "com.paymybuddy.PayMyBuddy.repository")
public class PayMyBuddyApplication implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger("main");
    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankAccountService bankAccountService;


    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

    }


}
