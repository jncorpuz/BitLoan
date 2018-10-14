package com.mayors.loditech.bitloan.Models;

public class User {
    public Account Account;
    public Wallet Wallet;
    public Rewards Rewards;

    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account account) {
        this.Account = account;
    }

    public Wallet getWallet() {
        return Wallet;
    }

    public void setWallet(Wallet wallet) {
        this.Wallet = wallet;
    }

    public Rewards getRewards() {
        return Rewards;
    }

    public void setRewards(Rewards rewards) {
        this.Rewards = rewards;
    }

    public User(Account account, Wallet wallet, Rewards rewards) {
        this.Account = account;
        this.Wallet = wallet;
        this.Rewards = rewards;
    }


}
