package com.paymybuddy.PayMyBuddy.model;

import jakarta.persistence.*;


@Entity
@Table(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private int bankAccountId;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bic")
    private String bic;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_wallet_id")
    private Wallet wallet;

}
