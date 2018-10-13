package com.example.loditech.bitloan.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wallet {
    @SerializedName("ID")
    @Expose
    public int ID;
    @SerializedName("AccountID")
    @Expose
    public int AccountID;
    @SerializedName("Amount")
    @Expose
    public double Amount;
    public static Wallet wallet;
    public Wallet(int ID, int accountID, double amount) {
        this.ID = ID;
        AccountID = accountID;
        Amount = amount;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }


}
