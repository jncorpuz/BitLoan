package com.mayors.loditech.bitloan.Models;

public class LoanLimits {
    public int ID;
    public int AccountID;
    public double MinimumAmount;
    public double MaximumAmount;

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

    public double getMinimumAmount() {
        return MinimumAmount;
    }

    public void setMinimumAmount(double minimumAmount) {
        MinimumAmount = minimumAmount;
    }

    public double getMaximumAmount() {
        return MaximumAmount;
    }

    public void setMaximumAmount(double maximumAmount) {
        MaximumAmount = maximumAmount;
    }


}
