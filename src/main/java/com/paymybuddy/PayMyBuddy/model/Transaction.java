package com.paymybuddy.PayMyBuddy.model;

import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "money_transaction")
@IdClass(TransactionId.class)
public  class Transaction {
    @Id
    @ManyToOne
    @JoinColumn(name = "wallet_creditor_id", referencedColumnName = "wallet_id")
    private Wallet walletCreditor;

    @Id
    @ManyToOne
    @JoinColumn(name = "wallet_debtor_id", referencedColumnName = "wallet_id")
    private Wallet walletDebtor;

    @Column(name = "taxe")
    private final float taxe = Data.TRANSACTION_TAXATION;

    @Column(name = "transaction_date")
    private Date date;

    @Column(name = "amount")
    private float amount;

    @Column(name = "is_bank_transaction")
    boolean isBankTransaction;

    public Wallet getWalletCreditor() {
        return walletCreditor;
    }

    public void setWalletCreditor(Wallet walletCreditor) {
        this.walletCreditor = walletCreditor;
    }

    public Wallet getWalletDebtor() {
        return walletDebtor;
    }

    public void setWalletDebtor(Wallet walletDebtor) {
        this.walletDebtor = walletDebtor;
    }

    public double getTaxe() {
        return taxe;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public boolean isBankTransaction() {
        return isBankTransaction;
    }

    public void setBankTransaction(boolean bankTransaction) {
        isBankTransaction = bankTransaction;
    }
}
