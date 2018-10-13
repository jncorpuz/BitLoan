package com.example.loditech.bitloan.Models;

import java.util.Date;

public class Loans {
    public int ID;//
    public int AccountID;
    public String DateofLoan;//
    public double Amount;
    public float InterestRate;//
    public int Term;//
    public String Reason;
    public boolean IsApproved;
    public boolean IsDeclined;
    public int ManagerID;//

    public Loans(int ID, int accountID, String dateofLoan, double amount, float interestRate, int term, String reason, boolean isApproved, boolean isDeclined, int managerID) {
        this.ID = ID;
        AccountID = accountID;
        DateofLoan = dateofLoan;
        Amount = amount;
        InterestRate = interestRate;
        Term = term;
        Reason = reason;
        IsApproved = isApproved;
        IsDeclined = isDeclined;
        ManagerID = managerID;
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

    public String getDateofLoan() {
        return DateofLoan;
    }

    public void setDateofLoan(String dateofLoan) {
        DateofLoan = dateofLoan;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public double getInterestRate() {
        return InterestRate;
    }

    public void setInterestRate(float interestRate) {
        InterestRate = interestRate;
    }

    public int getTerm() {
        return Term;
    }

    public void setTerm(int term) {
        Term = term;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public boolean isApproved() {
        return IsApproved;
    }

    public void setApproved(boolean approved) {
        IsApproved = approved;
    }

    public boolean isDeclined() {
        return IsDeclined;
    }

    public void setDeclined(boolean declined) {
        IsDeclined = declined;
    }

    public int getManagerID() {
        return ManagerID;
    }

    public void setManagerID(int managerID) {
        ManagerID = managerID;
    }
}
