package com.paymybuddy.PayMyBuddy;

import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.service.TransactionService;
import com.paymybuddy.PayMyBuddy.service.UserService;
import com.paymybuddy.PayMyBuddy.service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {
	private static final Logger logger = LogManager.getLogger("main");
	@Autowired
	private UserService userService;

	@Autowired
	private WalletService walletService;

	@Autowired
	private TransactionService transactionService;


	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Iterable<User> users = userService.getUsers();
		users.forEach(user -> System.out.println("user name : "  + user.getFirstname()));

		Iterable<Wallet> wallets = walletService.getWallets();
		wallets.forEach(wallet -> System.out.println("balance : "  +wallet.getBalance()));

		wallets.forEach(wallet -> System.out.println("transactions : "  +wallet.getCreditorTransactions()));

		Iterable<Transaction> transactions = transactionService.getTransactions();
		transactions.forEach(transaction -> System.out.println("amount : "  + transaction.getAmount()));
		transactions.forEach(transaction -> System.out.println("creditor : " + transaction.getWalletCreditor()));

	}
}
