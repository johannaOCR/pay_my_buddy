package com.paymybuddy.PayMyBuddy.model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@DynamicUpdate
@Table(name = "money_transaction")

public class Transaction {
    @Column(name = "taxe")
    private final float taxe = Data.TRANSACTION_TAXATION;
    @Column(name = "is_bank_transaction")
    boolean isBankTransaction;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;
    @ManyToOne
    @JoinColumn(name = "wallet_creditor_id", referencedColumnName = "wallet_id")
    private Wallet walletCreditor;
    @ManyToOne
    @JoinColumn(name = "wallet_debtor_id", referencedColumnName = "wallet_id")
    private Wallet walletDebtor;
    @Column(name = "transaction_date")
    private Date date;
    @Column(name = "amount")
    private float amount;
    @Column(name = "description")
    private String description;

    public Transaction() {
    }

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

    public String getDateFormated() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(this.date);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
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
