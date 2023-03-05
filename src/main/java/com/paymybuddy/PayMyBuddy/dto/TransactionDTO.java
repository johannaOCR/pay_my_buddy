package com.paymybuddy.PayMyBuddy.dto;

import java.text.DecimalFormat;

public class TransactionDTO {
    private String description;
    private String nameDebtor;
    private String nameCreditor;
    private String amount;

    public TransactionDTO(String description, String nameDebtor, String nameCreditor, float amount) {
        this.description = description;
        this.nameDebtor = nameDebtor;
        this.nameCreditor = nameCreditor;
        DecimalFormat df = new DecimalFormat("0.00"); // import java.text.DecimalFormat;
        this.amount = df.format(amount);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameDebtor() {
        if(this.nameCreditor.equals(this.nameDebtor)){
            return "Bank Account";
        }
        return nameDebtor;
    }

    public void setNameDebitor(String nameDebitor) {
        this.nameDebtor = nameDebitor;
    }

    public String getNameCreditor() {

        return nameCreditor;
    }

    public void setNameCreditor(String nameCreditor) {
        this.nameCreditor = nameCreditor;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        DecimalFormat df = new DecimalFormat("0.00"); // import java.text.DecimalFormat;
        this.amount = df.format(amount);
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "description='" + description + '\'' +
                ", nameDebtor='" + nameDebtor + '\'' +
                ", nameCreditor='" + nameCreditor + '\'' +
                ", amount=" + amount +
                '}';
    }
}
