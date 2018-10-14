package com.mayors.loditech.bitloan.Merchant;

import android.os.Bundle;

import com.mayors.loditech.bitloan.R;

public class Settings_Menu_Merchant extends BaseDrawerActivityMerchant {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.settings_menu, frameLayout);
        setTitle("Settings");
    }
}
