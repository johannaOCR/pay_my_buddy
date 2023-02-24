package com.paymybuddy.PayMyBuddy.model;

import java.io.Serializable;
import java.util.Objects;

public class TransactionId implements Serializable {
    private int walletCreditor;
    private int walletDebtor;

    public int getWalletCreditor() {
        return walletCreditor;
    }

    public void setWalletCreditor(int walletCreditor) {
        this.walletCreditor = walletCreditor;
    }

    public int getWalletDebtor() {
        return walletDebtor;
    }

    public void setWalletDebtor(int walletDebtor) {
        this.walletDebtor = walletDebtor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionId)) return false;
        TransactionId that = (TransactionId) o;
        return getWalletCreditor() == that.getWalletCreditor() && getWalletDebtor() == that.getWalletDebtor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWalletCreditor(), getWalletDebtor());
    }
}
