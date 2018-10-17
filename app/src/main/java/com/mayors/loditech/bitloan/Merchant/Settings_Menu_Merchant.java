package com.mayors.loditech.bitloan.Merchant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mayors.loditech.bitloan.R;

public class Settings_Menu_Merchant extends BaseDrawerActivityMerchant {
    Button cmdChangeEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.settings_menu, frameLayout);
        setTitle("Settings");

        cmdChangeEmail = findViewById(R.id.cmdChangeEmailMerchant);
    }

    public void cmdChangeEmail_Merchant(View v)
    {
        startActivity(new Intent(Settings_Menu_Merchant.this, Settings_A_Merchant.class));
    }

    public void ChangePassword_Merchant(View v)
    {
        startActivity(new Intent(Settings_Menu_Merchant.this, Settings_B_Merchant.class));
    }

    public void UpdateInformation_Merchant(View v)
    {
        startActivity(new Intent(Settings_Menu_Merchant.this, Settings_C_Merchant.class));
    }
}
