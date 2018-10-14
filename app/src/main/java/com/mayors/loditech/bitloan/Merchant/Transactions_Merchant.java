package com.mayors.loditech.bitloan.Merchant;

import android.os.Bundle;

import com.mayors.loditech.bitloan.R;

public class Transactions_Merchant extends BaseDrawerActivityMerchant {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.transactions_merchant, frameLayout);
        setTitle("Transactions");
    }
}
