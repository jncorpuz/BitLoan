package com.example.loditech.bitloan.Models;

import java.util.Date;

public class AuditLog {
    public int ID;
    public int AccountID;
    public String TimeStamp;

    public AuditLog(int ID, int accountID, String timeStamp) {
        this.ID = ID;
        AccountID = accountID;
        TimeStamp = timeStamp;
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

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }


}
