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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="fk_user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_bank_account_id")
    private BankAccount bankAccounts;

    @Column(name = "balance")
    private float balance;

    @OneToMany(mappedBy = "walletDebtor")
    List<Transaction> debtorTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "walletCreditor")
    List<Transaction> creditorTransactions = new ArrayList<>();


}
