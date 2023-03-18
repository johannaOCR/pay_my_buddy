package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.dto.ContactDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    private static final Logger logger = LogManager.getLogger("UserController");
    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/sign-up")
    public String signUp(@ModelAttribute User user) {
        return "signup";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user) {
        logger.info("Try to create new user" + user.toString());
        boolean isUserCreated = userService.addUser(user);
        if(isUserCreated){
            return "redirect:/login";
        } else {
            return "redirect:/sign-up";
        }

    }
    @GetMapping("/profile")
    public String profil(Principal principal, Model model){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user",user);
        model.addAttribute("bankAccount",user.getWallet().getBankAccounts());
        model.addAttribute("wallet",user.getWallet());
        return "profile";
    }
    @PostMapping("/addBankAccount")
    public String saveBankAccount(@RequestParam (name="bic") String bic, @RequestParam (name="iban") String iban,Principal principal ){
        User user = userService.getUserByEmail(principal.getName());
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban(iban);
        bankAccount.setBic(bic);
        user.getWallet().setBankAccounts(bankAccount);
        bankAccount.setWallet(user.getWallet());
        bankAccountService.saveBankAccount(bankAccount);
        userService.saveUser(user);
        return "redirect:/profile";
    }

    @PostMapping("/deleteBankAccount")
    public String deleteBankAccount(Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        int idBankAccountToDelete = user.getWallet().getBankAccounts().getBankAccountId();
        user.getWallet().setBankAccounts(null);
        userService.saveUser(user);
        boolean isOk = bankAccountService.deleteBankAccountById(idBankAccountToDelete);
        return "redirect:/profile";
    }


    @GetMapping("/contact")
    public String contact(Principal principal, Model model){
        List<ContactDTO> contacts = userService.getContactsByUser(principal);
        model.addAttribute("contacts", contacts);
        return "contact";
    }

    @PostMapping("/addContact")
    public String addContact(@RequestParam (name="email") String email,Principal principal ){
        User user = userService.getUserByEmail(principal.getName());
        User contact = userService.getUserByEmail(email);
        user.addContact(contact);
        userService.saveUser(user);
        return "redirect:/contact";
    }

    @PostMapping("/deleteContact")
    public String deleteBankAccount(@RequestParam (name="email") String email, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        User contact = userService.getUserByEmail(email);
        user.getContacts().remove(contact);
        userService.saveUser(user);
        return "redirect:/contact";
    }
    @PostMapping("/supplyBalance")
    public String supplyBalance(
            @RequestParam (name="cardName") String cardName,
            @RequestParam (name="cardNumber") String cardNumber,
            @RequestParam (name="expirationDate") String expirationDate,
            @RequestParam (name="CVV") String CVV,
            @RequestParam (name="amount") float amount,
            Principal principal ){
        User user = userService.getUserByEmail(principal.getName());
        Wallet wallet = user.getWallet();
        wallet.setBalance(wallet.getBalance()+amount);
        userService.saveUser(user);
        return "redirect:/profile";
    }

    @PostMapping("/transferToBank")
    public String transferToBank(
            @RequestParam (name="amount") float amount,
            Principal principal ){
        User user = userService.getUserByEmail(principal.getName());
        Wallet wallet = user.getWallet();
        if(wallet.getBalance()>=amount) {
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
            wallet.setBalance(wallet.getBalance()-amount);
            logger.info(wallet.getBalance());
            userService.saveUser(user);
            transactionService.saveTransaction(transaction);
            return "redirect:/profile";
        } else {
            logger.error("amount bigger than user's balance");
            return "redirect:/profile";
        }
    }

    @Transactional
    @PostMapping("/updateProfile")
    public String updateProfile(
            @RequestParam (name="lastname") String lastname,
            @RequestParam (name="firstname") String firstname,
            @RequestParam (name="bic") String bic,
            @RequestParam (name="iban") String iban,
            Principal principal )
    {
        User user = userService.getUserByEmail(principal.getName());
        if(lastname!="" || firstname!="" || bic!=""  || iban !=""  ){
            if(lastname!="" ){
                user.setLastname(lastname);
                logger.info("lastname updated : " + lastname);
            }
            if(firstname!="" ){
                user.setFirstname(firstname);
                logger.info("firstname updated : " + firstname);
            }
            if(bic!="" ){
                user.getWallet().getBankAccounts().setBic(bic);
                logger.info("bic updated : " + bic);
            }
            if(iban!="" ){
                user.getWallet().getBankAccounts().setIban(iban);
                logger.info("iban updated : " + iban);
            }
            userService.saveUser(user);
        }
        return "redirect:/profile";
    }

    @PostMapping("deleteUser")
    public String deleteUser(Principal principal){
        userService.deleteUser(userService.getUserByEmail(principal.getName()));
        return "redirect:/login";
    }
}
