package com.paymybuddy.PayMyBuddy.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private int walletId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_bank_account_id", referencedColumnName = "bank_account_id")
    private BankAccount bankAccounts;

    @Column(name = "balance")
    private float balance;

    @OneToMany(mappedBy = "walletDebtor")
    List<Transaction> debtorTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "walletCreditor")
    List<Transaction> creditorTransactions = new ArrayList<>();

    public Wallet() {
    }

    public Wallet(int walletId, User user, BankAccount bankAccounts, float balance, List<Transaction> debtorTransactions, List<Transaction> creditorTransactions) {
        this.walletId = walletId;
        this.user = user;
        this.bankAccounts = bankAccounts;
        this.balance = balance;
        this.debtorTransactions = debtorTransactions;
        this.creditorTransactions = creditorTransactions;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BankAccount getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(BankAccount bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public List<Transaction> getDebtorTransactions() {
        return debtorTransactions;
    }

    public void setDebtorTransactions(List<Transaction> debtorTransactions) {
        this.debtorTransactions = debtorTransactions;
    }

    public List<Transaction> getCreditorTransactions() {
        return creditorTransactions;
    }

    public void setCreditorTransactions(List<Transaction> creditorTransactions) {
        this.creditorTransactions = creditorTransactions;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", user=" + user.getUserId() +
                ", bankAccounts=" + bankAccounts.getBankAccountId() +
                ", balance=" + balance +
                '}';
    }
}
