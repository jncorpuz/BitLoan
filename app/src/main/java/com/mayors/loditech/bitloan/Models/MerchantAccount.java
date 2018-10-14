package com.mayors.loditech.bitloan.Models;

public class MerchantAccount extends Account {
    public int MerchantID;
    public int AccountID;
    public String EnterpriseName;
    public String EnterpriseAddress;
    public String EnterpriseDescription;
    public static MerchantAccount merchantAccount;

    public MerchantAccount(int ID, String username, String password, String firstName, String middleName, String lastName, String emailAddress, String address, String role, int merchantID, int accountID, String enterpriseName, String enterpriseAddress, String enterpriseDescription) {
        super(ID, username, password, firstName, middleName, lastName, emailAddress, address, role);
        MerchantID = merchantID;
        AccountID = accountID;
        EnterpriseName = enterpriseName;
        EnterpriseAddress = enterpriseAddress;
        EnterpriseDescription = enterpriseDescription;
    }

    public int getMerchantID() {
        return MerchantID;
    }

    public void setMerchantID(int merchantID) {
        MerchantID = merchantID;
    }

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public String getEnterpriseName() {
        return EnterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        EnterpriseName = enterpriseName;
    }

    public String getEnterpriseAddress() {
        return EnterpriseAddress;
    }

    public void setEnterpriseAddress(String enterpriseAddress) {
        EnterpriseAddress = enterpriseAddress;
    }

    public String getEnterpriseDescription() {
        return EnterpriseDescription;
    }

    public void setEnterpriseDescription(String enterpriseDescription) {
        EnterpriseDescription = enterpriseDescription;
    }




}
