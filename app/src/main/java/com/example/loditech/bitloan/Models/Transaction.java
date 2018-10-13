package com.example.loditech.bitloan.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Transaction {
    @SerializedName("ID")
    @Expose
    public int ID;
    @SerializedName("ReferenceID")
    @Expose
    public String ReferenceID;
    @SerializedName("TransactionDateTime")
    @Expose
    public String TransactionDateTime;
    @SerializedName("SenderID")
    @Expose
    public int SenderID;
    @SerializedName("ReceiverID")
    @Expose
    public int ReceiverID;
    @SerializedName("Amount")
    @Expose
    public double Amount;
    public static List<Transaction> transaction;
    public static List<String> EnterpriseNames;

    public Transaction(int ID, String referenceID, String transactionDateTime, int senderID, int receiverID, double amount) {
        this.ID = ID;
        ReferenceID = referenceID;
        TransactionDateTime = transactionDateTime;
        SenderID = senderID;
        ReceiverID = receiverID;
        Amount = amount;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getReferenceID() {
        return ReferenceID;
    }

    public void setReferenceID(String referenceID) {
        ReferenceID = referenceID;
    }

    public String getTransactionDateTime() {
        return TransactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        TransactionDateTime = transactionDateTime;
    }

    public int getSenderID() {
        return SenderID;
    }

    public void setSenderID(int senderID) {
        SenderID = senderID;
    }

    public int getReceiverID() {
        return ReceiverID;
    }

    public void setReceiverID(int receiverID) {
        ReceiverID = receiverID;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }
}
