package com.mayors.loditech.bitloan;

import android.content.Intent;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mayors.loditech.bitloan.Data.RetrofitClient;
import com.mayors.loditech.bitloan.Models.Account;
import com.mayors.loditech.bitloan.Models.Rewards;
import com.mayors.loditech.bitloan.Models.User;
import com.mayors.loditech.bitloan.Models.Wallet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings_Menu extends BaseDrawerActivity {
    TextView balance;
    ImageView btnBack6;
    Button btnChangeEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.settings_menu, frameLayout);
        GetUser();
        balance = (TextView) findViewById(R.id.txtBalance1);
        balance.setText(Double.toString(Wallet.wallet.getAmount()));
        btnBack6 = (ImageView) findViewById(R.id.btnBack6);
        btnChangeEmail = (Button) findViewById(R.id.btnChangeEmail);
        
    }

    public void GetUser(){
        Call<User> call = RetrofitClient.getInstance().getAPI().GetUser(Account.account.getID());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Wallet.wallet = user.Wallet;
                Rewards.rewards = user.Rewards;
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ScreenHeight = PxToDp(drawerLayout.getHeight());
        ScreenWidth = PxToDp(drawerLayout.getWidth()) ;

        ConstraintLayout.LayoutParams btnbackParams = (ConstraintLayout.LayoutParams)btnBack6.getLayoutParams();
        btnbackParams.setMargins(Scale(16, ScreenWidth, defWidth),Scale(32, ScreenHeight, defHeight),0,0);
        btnBack6.setLayoutParams(btnbackParams);

        ConstraintLayout.LayoutParams balanceParams = (ConstraintLayout.LayoutParams)balance.getLayoutParams();
        balanceParams.setMargins(0,Scale(40, ScreenHeight, defHeight),Scale(16, ScreenWidth, defWidth),0);
        balance.setLayoutParams(balanceParams);

        ConstraintLayout.LayoutParams changeemailParams = (ConstraintLayout.LayoutParams)btnChangeEmail.getLayoutParams();
        changeemailParams.setMargins(0,Scale(152, ScreenHeight, defHeight),0,0);
        btnChangeEmail.setLayoutParams(changeemailParams);

    }

    public void cmdChangeEmail(View v){
        Intent i = new Intent(Settings_Menu.this, Settings_A.class);
        startActivity(i);
    }

    public  void cmdChangePassword(View v){
        Intent i = new Intent(Settings_Menu.this, Settings_B.class);
        startActivity(i);
    }

    public void cmdUpdateInfo(View v){
        Intent i = new Intent(Settings_Menu.this, Settings_C.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(6).setChecked(true);
    }
}
