package com.example.loditech.bitloan.Models;

public class LoanPayment {
    public int ID;
    public int LoanID;
    public double Amount;
    public String DueDate;
    public String PaymentDate;
    public boolean IsPaid;

    public LoanPayment(int ID, int loanID, double amount, String dueDate, String paymentDate, boolean isPaid) {
        this.ID = ID;
        LoanID = loanID;
        Amount = amount;
        DueDate = dueDate;
        PaymentDate = paymentDate;
        IsPaid = isPaid;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getLoanID() {
        return LoanID;
    }

    public void setLoanID(int loanID) {
        LoanID = loanID;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        PaymentDate = paymentDate;
    }

    public boolean isPaid() {
        return IsPaid;
    }

    public void setPaid(boolean paid) {
        IsPaid = paid;
    }
}
