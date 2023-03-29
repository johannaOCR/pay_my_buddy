package com.paymybuddy.PayMyBuddy.model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@DynamicUpdate
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_wallet_id", referencedColumnName = "wallet_id")
    private Wallet wallet;

    public int getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(int bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "bankAccountId=" + bankAccountId +
                ", iban='" + iban + '\'' +
                ", bic='" + bic + '\'' +
                ", wallet=" + wallet +
                '}';
    }
}
