package com.paymybuddy.PayMyBuddy;

import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.service.BankAccountService;
import com.paymybuddy.PayMyBuddy.service.TransactionService;
import com.paymybuddy.PayMyBuddy.service.UserService;
import com.paymybuddy.PayMyBuddy.service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@SpringBootApplication
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
/* READ
		Iterable<User> users = userService.getUsers();
		users.forEach(user -> System.out.println("user name : "  + user.getFirstname()));
		users.forEach(user -> System.out.println("user  : "  + user.toString()));

		Iterable<Wallet> wallets = walletService.getWallets();
		wallets.forEach(wallet -> System.out.println("balance : "  +wallet.getBalance()));

		wallets.forEach(wallet ->  wallet.getCreditorTransactions().forEach(transaction -> System.out.println(transaction.getDate())));
		wallets.forEach(wallet -> System.out.println("transactionsDebitor : "  +wallet.getDebtorTransactions()));
		wallets.forEach(wallet -> System.out.println("wallet : "  +wallet.toString()));


		Iterable<Transaction> transactions = transactionService.getTransactions();
		transactions.forEach(transaction -> System.out.println("amount : "  + transaction.getAmount()));
		transactions.forEach(transaction -> System.out.println("creditor : " + transaction.getWalletCreditor()));

		Iterable<BankAccount> bankAccounts = bankAccountService.getBankAccounts();
		bankAccounts.forEach(bankAccount -> System.out.println("bankAccount = " + bankAccount.toString()));
*/
/*
		// WRITE
		// Creation d'un user
		User user = new User();
		user.setFirstname("maxime");
		user.setLastname("pointet");
		user.setEmail("maxime.pointet@mail.fr");
		user.setPassword("maxouChut");
	 	// creaton d'un wallet & lien vers user
		Wallet wallet = new Wallet();
		wallet.setBalance(0);
		wallet.setUser(user);
		// Creation d'un bank account & lien vers wallet
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBic("tutumax");
		bankAccount.setIban("toto");
		bankAccount.setWallet(wallet);

		// update du lien de wallet vers bank account
		wallet.setBankAccounts(bankAccount);

		// Création d'une transaction entre 2 wallet
		Transaction transaction = new Transaction();
		transaction.setAmount(10);transaction.setDate(new Date());
		transaction.setBankTransaction(false);
		transaction.setWalletCreditor(wallet);
		Optional<Wallet> walletOptional = walletService.getWalletById(1);
		walletOptional.ifPresent(transaction::setWalletDebtor);

		// création du lien du user vers le wallet complet
		user.setWallet(wallet);

		//creation d'un contact
		Optional<User> user1 = userService.getUserById(1);
		user1.ifPresent(user::addContact);

		Optional<User> user2 = userService.getUserById(2);
		user2.ifPresent(user::addContact);

		// Le save User enregistre en cascade tous les objets liés (wallet et bank account)
		User userToDelete = userService.saveUser(user);

		// Ici une relation unidirectionnelle, donc c'est à la transaction d'enregister le lien.
		transactionService.saveTransaction(transaction);
*/
/* DELETE

		userService.deleteUserById(21);
		assert(userService.getUserById(21).isEmpty());
*/
	}


}
